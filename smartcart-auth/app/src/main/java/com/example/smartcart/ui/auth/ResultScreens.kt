package com.example.smartcart.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcart.ui.components.AuthScaffold
import com.example.smartcart.ui.components.CircleBadge
import com.example.smartcart.ui.components.LinkButton
import com.example.smartcart.ui.components.OutlineCard
import com.example.smartcart.ui.components.PrimaryButton
import com.example.smartcart.ui.components.ScreenTitle
import com.example.smartcart.ui.theme.Green
import com.example.smartcart.ui.theme.GreenSoft
import com.example.smartcart.ui.theme.Ink
import com.example.smartcart.ui.theme.Muted

@Composable
fun VerifySuccessScreen(
    vm: AuthViewModel,
    onBack: () -> Unit,
    onContinue: () -> Unit,
    onOtherAccount: () -> Unit,
) {
    val ui = vm.ui
    val roleLabel = ui.role.label

    AuthScaffold(onBack) {
        ScreenTitle(
            "Xác minh thành công!",
            "Chào ${givenNameOf(ui.fullName)}, thông tin liên hệ của bạn đã được xác minh. Bắt đầu với SmartCart thôi.",
        )
        OutlineCard {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircleBadge(initialsOf(ui.fullName))
                    Spacer(Modifier.width(14.dp))
                    Column(Modifier.weight(1f)) {
                        Text(ui.fullName.uppercase(), fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Ink)
                        Text(maskContact(ui.contact), fontSize = 13.sp, color = Muted)
                    }
                    Icon(Icons.Outlined.Verified, null, tint = Green, modifier = Modifier.size(22.dp))
                }
                Spacer(Modifier.height(14.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(GreenSoft)
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(Icons.Outlined.CheckCircle, null, tint = Green, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Vai trò đã xác thực: $roleLabel", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Green)
                }
            }
        }
        Spacer(Modifier.height(20.dp))
        PrimaryButton("Tiếp tục đến Trang chủ", onContinue, icon = Icons.AutoMirrored.Filled.ArrowForward)
        Spacer(Modifier.height(16.dp))
        Text(
            "Bạn sẽ được chuyển hướng tự động theo vai trò $roleLabel.",
            Modifier.fillMaxWidth(),
            fontSize = 12.sp,
            color = Muted,
            textAlign = TextAlign.Start,
        )
        Spacer(Modifier.height(8.dp))
        LinkButton("Đăng nhập bằng tài khoản khác", onOtherAccount)
    }
}

/** Chỗ giữ chỗ. Màn Trang chủ không thuộc phạm vi đăng ký / đăng nhập, bạn thay bằng màn thật. */
@Composable
fun HomePlaceholderScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Trang chủ", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Ink)
        Spacer(Modifier.height(8.dp))
        Text("Đăng nhập thành công. Thay màn hình này bằng Trang chủ thật.", color = Muted, textAlign = TextAlign.Center)
    }
}
