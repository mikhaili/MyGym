package com.mikhaili.mygym


import androidx.test.espresso.ViewInteraction
import com.mikhaili.mygym.AbstractPage

/**
 * @author Asatryan on 15.12.2022
 */
class MainPage : AbstractPage() {

    val mainText: ViewInteraction = R.id.mainTextView.view()
    val resetButton: ViewInteraction = R.id.resetButton.view()
}