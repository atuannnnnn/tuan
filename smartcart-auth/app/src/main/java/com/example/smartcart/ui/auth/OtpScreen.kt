package com.example.smartcart.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcart.ui.components.AuthScaffold
import com.example.smartcart.ui.components.ErrorBanner
import com.example.smartcart.ui.components.LinkButton
import com.example.smartcart.ui.components.OtpInput
import com.example.smartcart.ui.components.PrimaryButton
import com.example.smartcart.ui.components.ScreenTitle
import com.example.smartcart.ui.theme.Danger
import com.example.smartcart.ui.theme.Green
import com.example.smartcart.ui.theme.GreenSoft
import com.example.smartcart.ui.theme.Ink
import com.example.smartcart.ui.theme.Muted
import kotlinx.coroutines.delay

/** Hai trạng thái: nhập mã (đếm ngược gửi lại 30 giây) và mã hết hạn (ui.otpExpired). */
@Composable
fun OtpScreen(
    vm: AuthViewModel,
    onBack: () -> Unit,
    onVerify: () -> Unit,
    onChangeContact: () -> Unit,
    onSupport: () -> Unit = {},
) {
    val ui = vm.ui
    val expired = ui.otpExpired

    // Mỗi lần gửi mã mới (otpRound đổi) thì đồng hồ đếm lại từ 30
    var secondsLeft by remember(ui.otpRound) { mutableIntStateOf(30) }
    LaunchedEffect(ui.otpRound) {
        while (secondsLeft > 0) {
            delay(1000)
            secondsLeft--
        }
    }

    AuthScaffold(onBack) {
        if (expired) {
            ScreenTitle("Mã xác minh hết hạn")
            ErrorBanner("Mã xác thực đã hết hiệu lực. Vui lòng yêu cầu mã mới để tiếp tục.")
            Spacer(Modifier.height(20.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Mã xác minh", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Ink)
                Text("00:00 · Hết hạn", fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = Danger)
            }
        } else {
            Box(
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(GreenSoft),
                contentAlignment = Alignment.Center,
            ) {
                // Thay bằng minh họa điện thoại + tin nhắn từ Figma
                Text("📱", fontSize = 56.sp)
            }
            Spacer(Modifier.height(28.dp))
            ScreenTitle(
                "Nhập mã xác minh",
                "Mã 6 chữ số đã được gửi đến email/số điện thoại của bạn (Hiệu lực 5 phút).",
            )
            Text("Mã xác minh", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Ink)
        }
        Spacer(Modifier.height(10.dp))

        OtpInput(value = ui.otp, onValueChange = vm::onOtp, isError = expired || ui.otpError != null)
        if (ui.otpError != null) {
            Spacer(Modifier.height(8.dp))
            Text(ui.otpError, fontSize = 12.sp, color = Danger)
        }
        Spacer(Modifier.height(16.dp))

        if (expired) {
            PrimaryButton("Gửi mã mới", vm::resendOtp, icon = Icons.Outlined.Refresh)
            Spacer(Modifier.height(8.dp))
            LinkButton("Đổi Email / Số điện thoại", onChangeContact)
            LinkButton("Liên hệ hỗ trợ", onSupport)
        } else {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                TextButton(onClick = vm::resendOtp, enabled = secondsLeft == 0) {
                    Icon(Icons.Outlined.Timer, null, Modifier.size(18.dp), tint = if (secondsLeft == 0) Green else Muted)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        if (secondsLeft > 0) "Gửi lại mã OTP (00:%02d)".format(secondsLeft) else "Gửi lại mã OTP",
                        color = if (secondsLeft == 0) Green else Muted,
                        fontSize = 14.sp,
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            PrimaryButton("Xác nhận & Hoàn tất", onClick = { vm.verifyOtp(onVerify) })
            Spacer(Modifier.height(8.dp))
            LinkButton("Liên hệ hỗ trợ", onSupport)
        }
    }
}
