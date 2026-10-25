package com.example.smartcart.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartcart.ui.auth.AccountExistsScreen
import com.example.smartcart.ui.auth.AuthViewModel
import com.example.smartcart.ui.auth.CompleteRegistrationScreen
import com.example.smartcart.ui.auth.ForgotPasswordScreen
import com.example.smartcart.ui.auth.HomePlaceholderScreen
import com.example.smartcart.ui.auth.LoginScreen
import com.example.smartcart.ui.auth.LoginVariant
import com.example.smartcart.ui.auth.OtpScreen
import com.example.smartcart.ui.auth.RegisterScreen
import com.example.smartcart.ui.auth.ResetSentScreen
import com.example.smartcart.ui.auth.RoleSelectScreen
import com.example.smartcart.ui.auth.VerifySuccessScreen

object Routes {
    const val ROLE = "role"
    const val REGISTER = "register"
    const val COMPLETE = "complete"
    const val LOGIN = "login"
    const val SESSION = "session"
    const val OTP = "otp"
    const val FORGOT = "forgot"
    const val RESET_SENT = "reset_sent"
    const val EXISTS = "exists"
    const val SUCCESS = "success"
    const val HOME = "home"
}

/**
 * Một AuthViewModel dùng chung cho cả luồng (tạo ở đây, ngoài NavHost) để dữ liệu nhập ở màn này
 * còn nguyên khi sang màn sau, ví dụ email đã nhập ở Đăng ký được dùng lại ở màn OTP và Xác minh thành công.
 */
@Composable
fun AppNav(nav: NavHostController = rememberNavController()) {
    val vm: AuthViewModel = viewModel()

    fun goHome() = nav.navigate(Routes.HOME) {
        // Xóa toàn bộ màn auth khỏi back stack để bấm Back không quay lại form đăng nhập
        popUpTo(Routes.ROLE) { inclusive = true }
    }
    fun goLogin() = nav.navigate(Routes.LOGIN) { launchSingleTop = true }

    NavHost(navController = nav, startDestination = Routes.ROLE) {

        composable(Routes.ROLE) {
            RoleSelectScreen(
                selected = vm.ui.role,
                onSelect = vm::selectRole,
                onContinue = { nav.navigate(Routes.REGISTER) },
                onLogin = ::goLogin,
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                vm = vm,
                onBack = { nav.popBackStack() },
                onSubmit = {
                    vm.submitRegister(
                        onOtp = { nav.navigate(Routes.OTP) },
                        onExists = { nav.navigate(Routes.EXISTS) },
                    )
                },
                onGoogle = { vm.signInWithGoogle { nav.navigate(Routes.COMPLETE) } },
                onLogin = ::goLogin,
            )
        }

        composable(Routes.COMPLETE) {
            CompleteRegistrationScreen(
                vm = vm,
                onBack = { nav.popBackStack() },
                onFinish = { vm.finishGoogleSignUp { nav.navigate(Routes.SUCCESS) } },
                onOtherAccount = { nav.popBackStack() },
            )
        }

        composable(Routes.EXISTS) {
            AccountExistsScreen(
                contact = vm.ui.contact,
                onBack = { nav.popBackStack() },
                onLogin = {
                    vm.prefillLogin(vm.ui.contact)
                    goLogin()
                },
                onRecover = {
                    vm.prefillLogin(vm.ui.contact)
                    nav.navigate(Routes.FORGOT)
                },
                onUseOther = { nav.popBackStack() },
            )
        }

        composable(Routes.OTP) {
            OtpScreen(
                vm = vm,
                onBack = { nav.popBackStack() },
                onVerify = { nav.navigate(Routes.SUCCESS) },
                onChangeContact = { nav.popBackStack() },
            )
        }

        composable(Routes.SUCCESS) {
            VerifySuccessScreen(
                vm = vm,
                onBack = { nav.popBackStack() },
                onContinue = ::goHome,
                onOtherAccount = ::goLogin,
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                vm = vm,
                variant = LoginVariant.WELCOME,
                onBack = { nav.popBackStack() },
                onSubmit = { vm.submitLogin(onSuccess = ::goHome) },
                onForgot = { nav.navigate(Routes.FORGOT) },
                onGoogle = { vm.signInWithGoogle { nav.navigate(Routes.COMPLETE) } },
                onRegister = { nav.navigate(Routes.REGISTER) { launchSingleTop = true } },
            )
        }

        // Màn "Phiên đăng nhập hết hạn": điều hướng tới đây khi API trả 401 (token hết hạn).
        composable(Routes.SESSION) {
            LoginScreen(
                vm = vm,
                variant = LoginVariant.SESSION_EXPIRED,
                onBack = { nav.popBackStack() },
                onSubmit = { vm.submitLogin(onSuccess = { nav.popBackStack() }) },
                onForgot = { nav.navigate(Routes.FORGOT) },
                onGoogle = { vm.signInWithGoogle { nav.navigate(Routes.COMPLETE) } },
                onRegister = { nav.navigate(Routes.REGISTER) },
                onOtherAccount = ::goLogin,
            )
        }

        composable(Routes.FORGOT) {
            ForgotPasswordScreen(
                vm = vm,
                onBack = { nav.popBackStack() },
                onSend = { vm.sendReset { nav.navigate(Routes.RESET_SENT) } },
                onBackToLogin = ::goLogin,
            )
        }

        composable(Routes.RESET_SENT) {
            ResetSentScreen(
                onBack = { nav.popBackStack() },
                onBackToLogin = ::goLogin,
                onChangeContact = { nav.popBackStack() },
            )
        }

        composable(Routes.HOME) { HomePlaceholderScreen() }
    }
}
