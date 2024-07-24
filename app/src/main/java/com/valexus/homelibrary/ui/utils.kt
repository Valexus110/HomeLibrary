package com.valexus.homelibrary.ui

import android.util.Patterns
import java.util.regex.Pattern


fun CharSequence?.isValidEmail() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()


fun CharSequence?.isValidPassword() =
    !isNullOrEmpty() && this.length > 6 && Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{4,}$")
        .matcher(this).matches()
