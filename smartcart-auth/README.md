# SmartCart: giao diện Đăng ký / Đăng nhập (Jetpack Compose + MVVM)

## Cài vào project

1. Android Studio: New Project → **Empty Activity** (loại Compose, ngôn ngữ Kotlin).
2. Chép thư mục `app/src/main/java/com/example/smartcart/` đè lên package của bạn.
   Nếu package khác `com.example.smartcart`, dùng Edit → Find → Replace in Files để đổi
   `com.example.smartcart` thành package của bạn (kể cả dòng `package ...` đầu mỗi file).
3. Thêm thư viện vào `app/build.gradle.kts` (chọn phiên bản mới nhất Android Studio gợi ý):

```kotlin
dependencies {
    implementation(platform("androidx.compose:compose-bom:<bản mới nhất>"))
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.navigation:navigation-compose:<bản mới nhất>")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:<bản mới nhất>")
    implementation("androidx.activity:activity-compose:<bản mới nhất>")
}
```

4. `AndroidManifest.xml`: trong thẻ `<activity>` của MainActivity thêm
   `android:windowSoftInputMode="adjustResize"` để bàn phím không che ô nhập.
5. Sync Gradle rồi Run.

## Dữ liệu chạy thử (chưa có backend)

| Việc | Nhập |
|---|---|
| Tài khoản đã tồn tại | Đăng ký bằng `khanhhuyen@gmail.com` |
| Đăng nhập đúng | `khanhhuyen@gmail.com` / `12345678` |
| Đăng nhập sai | bất kỳ mật khẩu khác |
| OTP đúng | `123456` |
| OTP hết hạn | `000000` |
| OTP sai | bất kỳ mã 6 số khác |

## Cấu trúc

- `ui/theme`: màu, typography
- `ui/components`: nút, ô nhập, ô OTP, thanh trên, footer dùng chung
- `ui/auth`: `AuthViewModel` (state + kiểm tra dữ liệu) và các màn hình
- `navigation/AppNav.kt`: sơ đồ chuyển màn hình

## Việc còn lại

- Thay mọi chỗ ghi `TODO` trong `AuthViewModel` bằng lời gọi API thật (Repository).
- Thay emoji bằng ảnh xuất từ Figma (banner, minh họa OTP, logo Google).
- Gắn Google Sign-In thật (Credential Manager).
- Màn Trang chủ chưa làm, đang là `HomePlaceholderScreen`.
- Màn "Phiên đăng nhập hết hạn" đã có route `Routes.SESSION`, gọi khi API trả 401.
