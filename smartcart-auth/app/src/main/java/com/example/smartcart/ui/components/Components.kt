package com.example.smartcart.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcart.ui.theme.AppBackground
import com.example.smartcart.ui.theme.Danger
import com.example.smartcart.ui.theme.DangerSoft
import com.example.smartcart.ui.theme.Green
import com.example.smartcart.ui.theme.GreenSoft
import com.example.smartcart.ui.theme.Ink
import com.example.smartcart.ui.theme.Line
import com.example.smartcart.ui.theme.Muted

val FieldShape = RoundedCornerShape(14.dp)

// ---------- Khung màn hình ----------

/** Khung chung: thanh trên (logo + nút back), nội dung cuộn được, footer "Thông tin của bạn được bảo vệ". */
@Composable
fun AuthScaffold(
    onBack: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(AppBackground)
            .systemBarsPadding()
            .imePadding()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 56.dp)
        ) {
            AuthTopBar(onBack)
            Spacer(Modifier.height(24.dp))
            content()
        }
        SecurityFooter(Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun AuthTopBar(onBack: (() -> Unit)?) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (onBack != null) {
            Box(
                Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFFF0F3F2))
                    .clickable(onClick = onBack),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại", tint = Ink)
            }
            Spacer(Modifier.width(12.dp))
        }
        Box(
            Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Green),
            contentAlignment = Alignment.Center,
        ) {
            Icon(Icons.Outlined.ShoppingCart, null, tint = Color.White, modifier = Modifier.size(22.dp))
        }
        Spacer(Modifier.width(10.dp))
        Text("SmartCart", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Ink)
    }
}

@Composable
fun SecurityFooter(modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .background(AppBackground)
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(Icons.Outlined.Shield, null, tint = Muted, modifier = Modifier.size(14.dp))
        Spacer(Modifier.width(6.dp))
        Text("Thông tin của bạn được bảo vệ", fontSize = 12.sp, color = Muted)
    }
}

@Composable
fun ScreenTitle(title: String, subtitle: String? = null) {
    Text(title, style = MaterialTheme.typography.headlineMedium, color = Ink)
    if (subtitle != null) {
        Spacer(Modifier.height(8.dp))
        Text(subtitle, fontSize = 15.sp, lineHeight = 22.sp, color = Muted)
    }
    Spacer(Modifier.height(24.dp))
}

/** Khung ảnh minh họa. Thay emoji bằng ảnh xuất từ Figma (drawable) khi có. */
@Composable
fun HeroBanner(emoji: String, modifier: Modifier = Modifier, height: androidx.compose.ui.unit.Dp = 150.dp) {
    Box(
        modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(24.dp))
            .background(Brush.linearGradient(listOf(Color(0xFFF3E6D3), Color(0xFFE3EEE2)))),
        contentAlignment = Alignment.Center,
    ) {
        Text(emoji, fontSize = 56.sp)
    }
}

// ---------- Nút ----------

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Green, contentColor = Color.White),
    ) {
        if (icon != null) {
            Icon(icon, null, Modifier.size(18.dp))
            Spacer(Modifier.width(10.dp))
        }
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun SecondaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Line),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White, contentColor = Ink),
    ) {
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun LinkButton(text: String, onClick: () -> Unit) {
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        TextButton(onClick = onClick) {
            Text(text, color = Green, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
        }
    }
}

@Composable
fun GoogleButton(text: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Line),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White, contentColor = Ink),
    ) {
        // Tạm dùng chữ G. Thay bằng logo Google (drawable) theo brand guideline của Google.
        Text("G", color = Color(0xFF4285F4), fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(Modifier.width(12.dp))
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun OrDivider(text: String = "hoặc tiếp tục với") {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        HorizontalDivider(Modifier.weight(1f), color = Line)
        Text(text, Modifier.padding(horizontal = 12.dp), fontSize = 12.sp, color = Muted)
        HorizontalDivider(Modifier.weight(1f), color = Line)
    }
}

// ---------- Ô nhập ----------

@Composable
fun AppTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    error: String? = null,
    hint: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailing: @Composable (() -> Unit)? = null,
) {
    Column(modifier.fillMaxWidth()) {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Ink)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = Muted.copy(alpha = 0.8f)) },
            singleLine = true,
            isError = error != null,
            shape = FieldShape,
            trailingIcon = trailing,
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Green,
                unfocusedBorderColor = Line,
                errorBorderColor = Danger,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                errorContainerColor = Color.White,
                cursorColor = Green,
                errorCursorColor = Danger,
            ),
        )
        when {
            error != null -> {
                Spacer(Modifier.height(6.dp))
                Text(error, fontSize = 12.sp, color = Danger)
            }
            hint != null -> {
                Spacer(Modifier.height(6.dp))
                Text(hint, fontSize = 12.sp, color = Muted)
            }
        }
    }
}

@Composable
fun PasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    error: String? = null,
    imeAction: ImeAction = ImeAction.Done,
) {
    var visible by remember { mutableStateOf(false) }
    AppTextField(
        label = label,
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        error = error,
        keyboardType = KeyboardType.Password,
        imeAction = imeAction,
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        trailing = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { visible = !visible }) {
                    Icon(
                        if (visible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                        contentDescription = "Hiện hoặc ẩn mật khẩu",
                        tint = Muted,
                    )
                }
                if (error != null) {
                    Icon(Icons.Outlined.ErrorOutline, null, tint = Danger)
                    Spacer(Modifier.width(12.dp))
                }
            }
        },
    )
}

/** 6 ô nhập mã. Một BasicTextField ẩn giữ dữ liệu, các ô chỉ vẽ lại từng ký tự. */
@Composable
fun OtpInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    length: Int = 6,
    isError: Boolean = false,
) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    BasicTextField(
        value = value,
        onValueChange = { new ->
            if (new.length <= length && new.all { it.isDigit() }) onValueChange(new)
        },
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(length) { index ->
                    val isCurrent = index == value.length.coerceAtMost(length - 1)
                    val borderColor = when {
                        isError -> Danger
                        isCurrent -> Green
                        else -> Line
                    }
                    Box(
                        Modifier
                            .weight(1f)
                            .aspectRatio(0.85f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isError) DangerSoft else Color.White)
                            .border(if (isCurrent && !isError) 2.dp else 1.dp, borderColor, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = value.getOrNull(index)?.toString().orEmpty(),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isError) Danger else Ink,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        },
    )
}

// ---------- Khối giao diện nhỏ ----------

@Composable
fun ErrorBanner(text: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(FieldShape)
            .background(DangerSoft)
            .padding(14.dp),
        verticalAlignment = Alignment.Top,
    ) {
        Icon(Icons.Outlined.ErrorOutline, null, tint = Danger, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(10.dp))
        Text(text, color = Danger, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, lineHeight = 19.sp)
    }
}

@Composable
fun RoleCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(16.dp)
    Row(
        Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(if (selected) GreenSoft else Color.White)
            .border(if (selected) 1.5.dp else 1.dp, if (selected) Green else Line, shape)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(if (selected) Color.White else GreenSoft),
            contentAlignment = Alignment.Center,
        ) {
            Icon(icon, null, tint = Green, modifier = Modifier.size(22.dp))
        }
        Spacer(Modifier.width(14.dp))
        Column(Modifier.weight(1f)) {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Ink)
            Spacer(Modifier.height(2.dp))
            Text(subtitle, fontSize = 13.sp, color = Muted)
        }
        RadioButton(
            selected = selected,
            onClick = null,
            colors = RadioButtonDefaults.colors(selectedColor = Green, unselectedColor = Line),
        )
    }
}

@Composable
fun SegmentedTabs(options: List<String>, selectedIndex: Int, onSelect: (Int) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFE3E9E6))
            .padding(4.dp),
    ) {
        options.forEachIndexed { index, label ->
            val selected = index == selectedIndex
            Box(
                Modifier
                    .weight(1f)
                    .height(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (selected) Color.White else Color.Transparent)
                    .clickable { onSelect(index) },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    label,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (selected) Green else Muted,
                )
            }
        }
    }
}

@Composable
fun TermsCheck(checked: Boolean, onChange: (Boolean) -> Unit, showError: Boolean = false) {
    Column {
        Row(verticalAlignment = Alignment.Top) {
            Checkbox(
                checked = checked,
                onCheckedChange = onChange,
                colors = CheckboxDefaults.colors(checkedColor = Green, uncheckedColor = if (showError) Danger else Line),
            )
            val text = buildAnnotatedString {
                append("Tôi đồng ý với ")
                withStyle(SpanStyle(color = Green, fontWeight = FontWeight.Bold)) { append("Điều khoản sử dụng") }
                append(" và ")
                withStyle(SpanStyle(color = Green, fontWeight = FontWeight.Bold)) { append("Chính sách bảo mật") }
                append(" của SmartCart.")
            }
            Text(text, Modifier.padding(top = 12.dp), fontSize = 13.sp, lineHeight = 19.sp, color = Muted)
        }
        if (showError) {
            Text("Bạn cần đồng ý để tiếp tục.", Modifier.padding(start = 12.dp), fontSize = 12.sp, color = Danger)
        }
    }
}

/** Thẻ nền xanh nhạt, dùng cho "Đăng ký cho: ..." và các dòng trạng thái. */
@Composable
fun SoftCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier
            .fillMaxWidth()
            .clip(FieldShape)
            .background(GreenSoft)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) { content() }
}

/** Thẻ trắng viền mảnh. */
@Composable
fun OutlineCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .border(1.dp, Line, RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) { content() }
}

@Composable
fun CircleBadge(text: String) {
    Box(
        Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(GreenSoft),
        contentAlignment = Alignment.Center,
    ) {
        Text(text, color = Green, fontWeight = FontWeight.Bold, fontSize = 15.sp)
    }
}
