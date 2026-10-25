package com.example.smartcart.ui.auth

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

enum class UserRole(val label: String) {
    BUYER("Người mua"),
    DRIVER("Tài xế"),
}

enum class SignUpMethod { EMAIL, PHONE }

/** Toàn bộ state của luồng auth nằm một chỗ. Màn hình chỉ đọc state và gọi hàm của ViewModel. */
data class AuthUiState(
    // Đăng ký
    val role: UserRole = UserRole.BUYER,
    val method: SignUpMethod = SignUpMethod.EMAIL,
    val fullName: String = "",
    val contact: String = "",
    val password: String = "",
    val agreed: Boolean = false,
    val nameError: String? = null,
    val contactError: String? = null,
    val passwordError: String? = null,
    val termsError: Boolean = false,
    // Đăng nhập, quên mật khẩu
    val loginId: String = "",
    val loginPassword: String = "",
    val loginIdError: String? = null,
    val loginError: String? = null,
    val loginFailed: Boolean = false,
    // OTP
    val otp: String = "",
    val otpError: String? = null,
    val otpExpired: Boolean = false,
    val otpRound: Int = 0, // tăng mỗi lần gửi mã để màn hình tính lại đồng hồ đếm ngược
    // Google
    val googleEmail: String? = null,
)

/**
 * Dữ liệu giả để chạy thử giao diện khi chưa có backend.
 * Khi có API thật, thay các chỗ gắn nhãn TODO bằng lời gọi Repository.
 */
private object Demo {
    const val EXISTING_ID = "khanhhuyen@gmail.com"
    const val EXISTING_PASSWORD = "12345678"
    const val GOOD_OTP = "123456"
    const val EXPIRED_OTP = "000000"
    const val GOOGLE_NAME = "Đỗ Ngọc Khánh Huyền"
}

private val PHONE_REGEX = Regex("^(\\+84|0)\\d{9}$")

class AuthViewModel : ViewModel() {

    var ui by mutableStateOf(AuthUiState())
        private set

    // ---------- Chọn vai trò và đăng ký ----------

    fun selectRole(role: UserRole) {
        ui = ui.copy(
            role = role,
            // Tài xế đăng ký bằng số điện thoại theo thiết kế
            method = if (role == UserRole.DRIVER) SignUpMethod.PHONE else SignUpMethod.EMAIL,
            contact = "",
            contactError = null,
        )
    }

    fun setMethod(method: SignUpMethod) {
        ui = ui.copy(method = method, contact = "", contactError = null)
    }

    fun onFullName(v: String) { ui = ui.copy(fullName = v, nameError = null) }
    fun onContact(v: String) { ui = ui.copy(contact = v, contactError = null) }
    fun onPassword(v: String) { ui = ui.copy(password = v, passwordError = null) }
    fun onAgreed(v: Boolean) { ui = ui.copy(agreed = v, termsError = false) }

    fun submitRegister(onOtp: () -> Unit, onExists: () -> Unit) {
        val name = ui.fullName.trim()
        val contact = ui.contact.trim()

        val nameErr = if (name.isEmpty()) "Vui lòng nhập họ và tên." else null
        val contactErr = validateContact(contact, ui.method)
        val passErr = if (ui.password.length < 8) "Mật khẩu tối thiểu 8 ký tự." else null
        val termsErr = !ui.agreed

        ui = ui.copy(
            nameError = nameErr,
            contactError = contactErr,
            passwordError = passErr,
            termsError = termsErr,
        )
        if (nameErr != null || contactErr != null || passErr != null || termsErr) return

        // TODO: gọi API kiểm tra tài khoản đã tồn tại và gửi OTP
        if (contact.equals(Demo.EXISTING_ID, ignoreCase = true)) {
            onExists()
            return
        }
        resetOtp()
        onOtp()
    }

    // ---------- Google ----------

    fun signInWithGoogle(onNewUser: () -> Unit) {
        // TODO: thay bằng Credential Manager / Google Sign-In, lấy email và tên từ tài khoản Google
        ui = ui.copy(
            googleEmail = Demo.EXISTING_ID,
            contact = Demo.EXISTING_ID,
            fullName = Demo.GOOGLE_NAME,
            nameError = null,
            agreed = false,
            termsError = false,
        )
        onNewUser()
    }

    fun finishGoogleSignUp(onDone: () -> Unit) {
        val nameErr = if (ui.fullName.trim().isEmpty()) "Vui lòng nhập họ và tên." else null
        val termsErr = !ui.agreed
        ui = ui.copy(nameError = nameErr, termsError = termsErr)
        if (nameErr == null && !termsErr) onDone() // TODO: gửi tên + vai trò lên server
    }

    // ---------- Đăng nhập ----------

    fun onLoginId(v: String) { ui = ui.copy(loginId = v, loginIdError = null, loginError = null, loginFailed = false) }
    fun onLoginPassword(v: String) { ui = ui.copy(loginPassword = v, loginError = null, loginFailed = false) }

    fun prefillLogin(id: String) { ui = ui.copy(loginId = id, loginPassword = "", loginError = null, loginFailed = false) }

    fun submitLogin(onSuccess: () -> Unit) {
        val id = ui.loginId.trim()
        if (id.isEmpty()) {
            ui = ui.copy(loginIdError = "Vui lòng nhập email hoặc số điện thoại.")
            return
        }
        if (ui.loginPassword.isEmpty()) {
            ui = ui.copy(loginError = "Vui lòng nhập mật khẩu.")
            return
        }
        // TODO: gọi API đăng nhập
        if (id.equals(Demo.EXISTING_ID, ignoreCase = true) && ui.loginPassword == Demo.EXISTING_PASSWORD) {
            ui = ui.copy(loginError = null, loginFailed = false)
            onSuccess()
        } else {
            ui = ui.copy(loginError = "Mật khẩu không đúng.", loginFailed = true)
        }
    }

    // ---------- Quên mật khẩu ----------

    fun sendReset(onSent: () -> Unit) {
        val id = ui.loginId.trim()
        val valid = Patterns.EMAIL_ADDRESS.matcher(id).matches() || PHONE_REGEX.matches(id.replace(" ", ""))
        if (!valid) {
            ui = ui.copy(loginIdError = "Email hoặc số điện thoại không hợp lệ.")
            return
        }
        // TODO: gọi API gửi liên kết đặt lại mật khẩu
        onSent()
    }

    // ---------- OTP ----------

    fun onOtp(v: String) { ui = ui.copy(otp = v, otpError = null) }

    fun verifyOtp(onSuccess: () -> Unit) {
        // TODO: gọi API xác minh. Quy ước chạy thử: 123456 đúng, 000000 hết hạn, còn lại sai.
        when {
            ui.otp.length < 6 -> ui = ui.copy(otpError = "Vui lòng nhập đủ 6 chữ số.")
            ui.otp == Demo.EXPIRED_OTP ->
                ui = ui.copy(otpExpired = true, otpError = "Không thể xác minh bằng mã đã hết hạn.")
            ui.otp == Demo.GOOD_OTP -> onSuccess()
            else -> ui = ui.copy(otpError = "Mã không đúng. Vui lòng thử lại.")
        }
    }

    fun resendOtp() {
        // TODO: gọi API gửi lại mã
        resetOtp()
    }

    private fun resetOtp() {
        ui = ui.copy(otp = "", otpError = null, otpExpired = false, otpRound = ui.otpRound + 1)
    }

    // ---------- Kiểm tra dữ liệu ----------

    private fun validateContact(value: String, method: SignUpMethod): String? = when (method) {
        SignUpMethod.EMAIL -> when {
            value.isEmpty() -> "Vui lòng nhập email."
            !Patterns.EMAIL_ADDRESS.matcher(value).matches() -> "Email không hợp lệ."
            else -> null
        }
        SignUpMethod.PHONE -> when {
            value.isEmpty() -> "Vui lòng nhập số điện thoại."
            !PHONE_REGEX.matches(value.replace(" ", "")) -> "Số điện thoại không hợp lệ."
            else -> null
        }
    }
}

// ---------- Hàm tiện ích hiển thị ----------

/** k********n@gmail.com. Với số điện thoại thì chỉ giữ 3 số cuối. */
fun maskContact(contact: String): String {
    val at = contact.indexOf('@')
    if (at > 1) {
        val local = contact.substring(0, at)
        val stars = "*".repeat((local.length - 2).coerceAtLeast(1))
        return "${local.first()}$stars${local.last()}${contact.substring(at)}"
    }
    val digits = contact.filter { it.isDigit() }
    return if (digits.length > 3) "*".repeat(digits.length - 3) + digits.takeLast(3) else contact
}

private fun nameParts(name: String) = name.trim().split(Regex("\\s+")).filter { it.isNotEmpty() }

/** "Đỗ Ngọc Khánh Huyền" -> "KH" */
fun initialsOf(name: String): String =
    nameParts(name).takeLast(2).joinToString("") { it.first().uppercase() }

/** "Đỗ Ngọc Khánh Huyền" -> "Khánh Huyền" (tên gọi theo thói quen người Việt) */
fun givenNameOf(name: String): String = nameParts(name).takeLast(2).joinToString(" ")
