package com.example.smartcart.ui.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.ShoppingBasket
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcart.ui.components.AppTextField
import com.example.smartcart.ui.components.AuthScaffold
import com.example.smartcart.ui.components.GoogleButton
import com.example.smartcart.ui.components.LinkButton
import com.example.smartcart.ui.components.OrDivider
import com.example.smartcart.ui.components.OutlineCard
import com.example.smartcart.ui.components.PasswordField
import com.example.smartcart.ui.components.PrimaryButton
import com.example.smartcart.ui.components.ScreenTitle
import com.example.smartcart.ui.theme.Green
import com.example.smartcart.ui.theme.GreenSoft
import com.example.smartcart.ui.theme.Ink
import com.example.smartcart.ui.theme.Muted

enum class LoginVariant { WELCOME, SESSION_EXPIRED }

/**
 * Một màn hình, ba trạng thái trong thiết kế:
 *  - WELCOME: "Chào mừng trở lại!"
 *  - WELCOME + lỗi (ui.loginFailed): "Đăng nhập thất bại", ô mật khẩu đỏ
 *  - SESSION_EXPIRED: "Phiên đăng nhập hết hạn", có thẻ giỏ hàng đang dở
 * Các trạng thái chỉ khác nhau ở tiêu đề và vài khối phụ, nên gộp một composable để không lặp code form.
 */
@Composable
fun LoginScreen(
    vm: AuthViewModel,
    variant: LoginVariant = LoginVariant.WELCOME,
    cartCount: Int = 3,
    onBack: () -> Unit,
    onSubmit: () -> Unit,
    onForgot: () -> Unit,
    onGoogle: () -> Unit,
    onRegister: () -> Unit,
    onSupport: () -> Unit = {},
    onOtherAccount: () -> Unit = {},
) {
    val ui = vm.ui
    val expired = variant == LoginVariant.SESSION_EXPIRED
    val failed = !expired && ui.loginFailed

    AuthScaffold(onBack) {
        when {
            expired -> ScreenTitle("Phiên đăng nhập hết hạn", "Xác nhận mật khẩu để tiếp tục thao tác dở dang.")
            failed -> ScreenTitle("Đăng nhập thất bại", "Tên tài khoản hoặc mật khẩu không chính xác. Vui lòng thử lại.")
            else -> {
                PromoBanner()
                Spacer(Modifier.height(24.dp))
                ScreenTitle("Chào mừng trở lại!", "Đăng nhập để tiếp tục lên danh sách đi chợ.")
            }
        }

        if (expired) {
            OutlineCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.ShoppingBasket, null, tint = Green, modifier = Modifier.size(22.dp))
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text("BẠN ĐANG TIẾP TỤC", fontSize = 11.sp, color = Muted, letterSpacing = 0.6.sp)
                        Text("Giỏ hàng · $cartCount sản phẩm", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Ink)
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
        }

        AppTextField(
            label = if (expired || failed) "Email hoặc số điện thoại *" else "Email",
            value = ui.loginId,
            onValueChange = vm::onLoginId,
            placeholder = "Nhập địa chỉ email",
            error = ui.loginIdError,
            keyboardType = KeyboardType.Email,
        )
        Spacer(Modifier.height(16.dp))
        PasswordField(
            label = if (expired || failed) "Mật khẩu *" else "Mật khẩu",
            value = ui.loginPassword,
            onValueChange = vm::onLoginPassword,
            placeholder = if (expired || failed) "Nhập lại mật khẩu" else "Nhập mật khẩu",
            error = ui.loginError,
        )
        Text(
            "Quên mật khẩu?",
            Modifier
                .align(Alignment.End)
                .clickable(onClick = onForgot)
                .padding(vertical = 14.dp),
            color = Green,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
        )
        PrimaryButton(
            "Đăng nhập",
            onSubmit,
            icon = if (!expired && !failed) Icons.AutoMirrored.Filled.ArrowForward else null,
        )

        if (failed) {
            Spacer(Modifier.height(12.dp))
            LinkButton("Chưa có tài khoản? Đăng ký", onRegister)
            LinkButton("Liên hệ hỗ trợ", onSupport)
        } else {
            OrDivider()
            GoogleButton("Đăng nhập bằng Google", onGoogle)
            Spacer(Modifier.height(12.dp))
            if (expired) {
                LinkButton("Dùng tài khoản khác", onOtherAccount)
            } else {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center) {
                    Text("Chưa có tài khoản? ", color = Muted, fontSize = 14.sp, modifier = Modifier.padding(top = 12.dp))
                    Text(
                        "Đăng ký ngay",
                        Modifier
                            .clickable(onClick = onRegister)
                            .padding(top = 12.dp),
                        color = Green,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                    )
                }
            }
        }
    }
}

@Composable
private fun PromoBanner() {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(GreenSoft)
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            Text("TƯƠI NGON MỖI NGÀY", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Green, letterSpacing = 0.6.sp)
            Spacer(Modifier.height(6.dp))
            Text("Đi chợ nhẹ nhàng.\nBữa ăn trọn vẹn.", fontSize = 17.sp, lineHeight = 23.sp, color = Ink)
            Spacer(Modifier.height(6.dp))
            Text("Có SmartCart đồng hành.", fontSize = 12.sp, color = Green)
        }
        // Thay bằng ảnh túi rau củ xuất từ Figma
        Text("🛍️", fontSize = 56.sp, color = Color.Unspecified)
    }
}
