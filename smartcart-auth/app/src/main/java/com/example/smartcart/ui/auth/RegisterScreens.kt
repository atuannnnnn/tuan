package com.example.smartcart.ui.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.PedalBike
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcart.ui.components.AppTextField
import com.example.smartcart.ui.components.AuthScaffold
import com.example.smartcart.ui.components.GoogleButton
import com.example.smartcart.ui.components.HeroBanner
import com.example.smartcart.ui.components.LinkButton
import com.example.smartcart.ui.components.OrDivider
import com.example.smartcart.ui.components.PasswordField
import com.example.smartcart.ui.components.PrimaryButton
import com.example.smartcart.ui.components.RoleCard
import com.example.smartcart.ui.components.ScreenTitle
import com.example.smartcart.ui.components.SegmentedTabs
import com.example.smartcart.ui.components.SoftCard
import com.example.smartcart.ui.components.TermsCheck
import com.example.smartcart.ui.theme.Green
import com.example.smartcart.ui.theme.GreenSoft
import com.example.smartcart.ui.theme.Ink
import com.example.smartcart.ui.theme.Line
import com.example.smartcart.ui.theme.Muted

// ---------- Màn 1: Chọn vai trò ----------

@Composable
fun RoleSelectScreen(
    selected: UserRole,
    onSelect: (UserRole) -> Unit,
    onContinue: () -> Unit,
    onLogin: () -> Unit,
) {
    AuthScaffold {
        HeroBanner(emoji = "🛍️🛵")
        Spacer(Modifier.height(20.dp))
        Text(
            "CHÀO BẠN ĐẾN VỚI SMARTCART",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Green,
            letterSpacing = 0.8.sp,
        )
        Spacer(Modifier.height(6.dp))
        ScreenTitle("Chọn vai trò của bạn", "Mua sắm tiện lợi hoặc giao hàng linh hoạt.")

        RoleCard(
            title = "Người mua",
            subtitle = "Mua sắm tiện lợi, giao tận nơi",
            icon = Icons.Outlined.ShoppingBag,
            selected = selected == UserRole.BUYER,
            onClick = { onSelect(UserRole.BUYER) },
        )
        Spacer(Modifier.height(12.dp))
        RoleCard(
            title = "Tài xế",
            subtitle = "Nhận đơn, giao hàng chủ động",
            icon = Icons.Outlined.PedalBike,
            selected = selected == UserRole.DRIVER,
            onClick = { onSelect(UserRole.DRIVER) },
        )
        Spacer(Modifier.height(24.dp))
        PrimaryButton("Tiếp tục", onContinue, icon = Icons.AutoMirrored.Filled.ArrowForward)
        Spacer(Modifier.height(8.dp))
        LinkButton("Đã có tài khoản? Đăng nhập", onLogin)
    }
}

// ---------- Màn 2: Tạo tài khoản (tab Email / Số điện thoại) ----------

@Composable
fun RegisterScreen(
    vm: AuthViewModel,
    onBack: () -> Unit,
    onSubmit: () -> Unit,
    onGoogle: () -> Unit,
    onLogin: () -> Unit,
) {
    val ui = vm.ui
    val isEmail = ui.method == SignUpMethod.EMAIL

    AuthScaffold(onBack) {
        // Nhãn nhỏ "Khởi đầu tươi mới"
        Row(
            Modifier
                .clip(RoundedCornerShape(50))
                .background(GreenSoft)
                .padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(Icons.Outlined.Eco, null, tint = Green, modifier = Modifier.size(14.dp))
            Spacer(Modifier.width(6.dp))
            Text("Khởi đầu tươi mới", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Green)
        }
        Spacer(Modifier.height(12.dp))

        ScreenTitle(
            "Tạo tài khoản",
            if (ui.role == UserRole.DRIVER) "Dùng số điện thoại của bạn để đăng ký tài khoản Tài xế."
            else "Tạo tài khoản để bắt đầu mua sắm dễ dàng hơn.",
        )

        SoftCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Person, null, tint = Green, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(10.dp))
                Text(
                    "Đăng ký cho: ${ui.role.label}",
                    Modifier.weight(1f),
                    fontWeight = FontWeight.SemiBold,
                    color = Green,
                    fontSize = 14.sp,
                )
                Text("Đổi", Modifier.clickable(onClick = onBack), fontWeight = FontWeight.Bold, color = Green, fontSize = 14.sp)
            }
        }
        Spacer(Modifier.height(16.dp))

        SegmentedTabs(
            options = listOf("Email", "Số điện thoại"),
            selectedIndex = if (isEmail) 0 else 1,
            onSelect = { vm.setMethod(if (it == 0) SignUpMethod.EMAIL else SignUpMethod.PHONE) },
        )
        Spacer(Modifier.height(16.dp))

        AppTextField(
            label = "Họ và tên *",
            value = ui.fullName,
            onValueChange = vm::onFullName,
            placeholder = "Nhập họ và tên",
            error = ui.nameError,
        )
        Spacer(Modifier.height(16.dp))
        AppTextField(
            label = if (isEmail) "Email *" else "Số điện thoại *",
            value = ui.contact,
            onValueChange = vm::onContact,
            placeholder = if (isEmail) "Nhập địa chỉ email" else "Nhập số điện thoại",
            error = ui.contactError,
            keyboardType = if (isEmail) KeyboardType.Email else KeyboardType.Phone,
        )
        Spacer(Modifier.height(16.dp))
        PasswordField(
            label = "Mật khẩu *",
            value = ui.password,
            onValueChange = vm::onPassword,
            placeholder = "Tạo mật khẩu",
            error = ui.passwordError,
        )
        Spacer(Modifier.height(8.dp))
        TermsCheck(ui.agreed, vm::onAgreed, showError = ui.termsError)
        Spacer(Modifier.height(8.dp))
        PrimaryButton(
            if (isEmail) "Tạo tài khoản bằng email" else "Tạo tài khoản bằng điện thoại",
            onSubmit,
        )
        OrDivider()
        GoogleButton("Đăng nhập bằng Google", onGoogle)
        Spacer(Modifier.height(8.dp))
        LinkButton("Đã có tài khoản? Đăng nhập", onLogin)
    }
}

// ---------- Màn 3: Hoàn tất đăng ký (sau khi chọn Google) ----------

@Composable
fun CompleteRegistrationScreen(
    vm: AuthViewModel,
    onBack: () -> Unit,
    onFinish: () -> Unit,
    onOtherAccount: () -> Unit,
) {
    val ui = vm.ui
    AuthScaffold(onBack) {
        ScreenTitle("Hoàn tất đăng ký", "Chọn vai trò sử dụng để hoàn tất thiết lập tài khoản.")

        // Thẻ danh tính Google
        Row(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .border(1.dp, Line, RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("G", color = Color(0xFF4285F4), fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(Modifier.width(12.dp))
            androidx.compose.foundation.layout.Column(Modifier.weight(1f)) {
                Text(ui.googleEmail.orEmpty(), fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Ink)
                Text("Danh tính đã được SmartCart xác thực", fontSize = 12.sp, color = Muted)
            }
            Icon(Icons.Outlined.CheckCircle, null, tint = Green, modifier = Modifier.size(20.dp))
        }
        Spacer(Modifier.height(16.dp))

        AppTextField(
            label = "Họ và tên *",
            value = ui.fullName,
            onValueChange = vm::onFullName,
            placeholder = "Nhập họ và tên",
            error = ui.nameError,
            hint = "Kiểm tra lại tên hiển thị của bạn.",
        )
        Spacer(Modifier.height(16.dp))

        Text("Bạn sẽ dùng SmartCart để… *", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Ink)
        Spacer(Modifier.height(8.dp))
        RoleCard(
            "Người mua", "Mua sắm tiện lợi, giao tận nơi", Icons.Outlined.ShoppingBag,
            selected = ui.role == UserRole.BUYER, onClick = { vm.selectRole(UserRole.BUYER) },
        )
        Spacer(Modifier.height(12.dp))
        RoleCard(
            "Tài xế", "Nhận đơn, giao hàng chủ động", Icons.Outlined.PedalBike,
            selected = ui.role == UserRole.DRIVER, onClick = { vm.selectRole(UserRole.DRIVER) },
        )
        Spacer(Modifier.height(8.dp))
        TermsCheck(ui.agreed, vm::onAgreed, showError = ui.termsError)
        Spacer(Modifier.height(8.dp))
        PrimaryButton("Hoàn tất tài khoản", onFinish)
        Spacer(Modifier.height(8.dp))
        LinkButton("Dùng tài khoản khác", onOtherAccount)
    }
}
