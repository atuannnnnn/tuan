package com.example.smartcart.ui.auth

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcart.ui.components.AppTextField
import com.example.smartcart.ui.components.AuthScaffold
import com.example.smartcart.ui.components.LinkButton
import com.example.smartcart.ui.components.OutlineCard
import com.example.smartcart.ui.components.PrimaryButton
import com.example.smartcart.ui.components.ScreenTitle
import com.example.smartcart.ui.components.SecondaryButton
import com.example.smartcart.ui.theme.Ink

// ---------- Đặt lại mật khẩu ----------

@Composable
fun ForgotPasswordScreen(
    vm: AuthViewModel,
    onBack: () -> Unit,
    onSend: () -> Unit,
    onBackToLogin: () -> Unit,
    onSupport: () -> Unit = {},
) {
    val ui = vm.ui
    AuthScaffold(onBack) {
        ScreenTitle("Đặt lại mật khẩu")
        AppTextField(
            label = "Email hoặc số điện thoại",
            value = ui.loginId,
            onValueChange = vm::onLoginId,
            placeholder = "Nhập email hoặc số điện thoại",
            error = ui.loginIdError,
            hint = "Ví dụ: dnkh@gmail.com hoặc +84 912 345 678.",
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done,
        )
        Spacer(Modifier.height(16.dp))
        PrimaryButton("Gửi mã xác minh", onSend)
        Spacer(Modifier.height(12.dp))
        LinkButton("Quay lại Đăng nhập", onBackToLogin)
        LinkButton("Liên hệ hỗ trợ", onSupport)
    }
}

@Composable
fun ResetSentScreen(
    onBack: () -> Unit,
    onBackToLogin: () -> Unit,
    onChangeContact: () -> Unit,
) {
    AuthScaffold(onBack) {
        ScreenTitle("Đã gửi hướng dẫn", "Chúng tôi đã gửi liên kết đặt lại mật khẩu đến Email/Số điện thoại của bạn.")
        OutlineCard {
            androidx.compose.foundation.layout.Column {
                Text("1. Nhấp vào liên kết trong tin nhắn để tạo mật khẩu mới.", fontSize = 14.sp, lineHeight = 21.sp, color = Ink)
                Spacer(Modifier.height(14.dp))
                Text("2. Quay lại SmartCart để đăng nhập.", fontSize = 14.sp, lineHeight = 21.sp, color = Ink)
            }
        }
        Spacer(Modifier.height(20.dp))
        PrimaryButton("Quay lại đăng nhập", onBackToLogin)
        Spacer(Modifier.height(8.dp))
        LinkButton("Đổi Email / Số điện thoại", onChangeContact)
    }
}

// ---------- Tài khoản đã tồn tại ----------

@Composable
fun AccountExistsScreen(
    contact: String,
    onBack: () -> Unit,
    onLogin: () -> Unit,
    onRecover: () -> Unit,
    onUseOther: () -> Unit,
) {
    AuthScaffold(onBack) {
        ScreenTitle("Tài khoản đã tồn tại", "$contact đã được đăng ký. Vui lòng đăng nhập hoặc khôi phục truy cập.")
        PrimaryButton("Đăng nhập ngay", onLogin)
        Spacer(Modifier.height(12.dp))
        SecondaryButton("Khôi phục mật khẩu", onRecover)
        Spacer(Modifier.height(12.dp))
        LinkButton("Dùng email hoặc số điện thoại khác", onUseOther)
    }
}
