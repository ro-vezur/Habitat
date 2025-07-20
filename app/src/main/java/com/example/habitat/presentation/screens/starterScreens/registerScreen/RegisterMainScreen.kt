package com.example.habitat.presentation.screens.starterScreens.registerScreen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.habitat.REGISTER_PAGE_COUNT
import com.example.habitat.presentation.commonComponents.buttons.TurnBackButton
import com.example.habitat.presentation.screens.starterScreens.registerScreen.stepsScreens.Step1screen
import com.example.habitat.ui.theme.HabitatTheme
import com.example.habitat.ui.theme.materialThemeExtensions.responsiveLayout
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.example.habitat.INITIAL_REGISTER_PAGE
import com.example.habitat.presentation.ScreensRoutes
import com.example.habitat.presentation.screens.starterScreens.registerScreen.components.SkipProceedButtons
import com.example.habitat.presentation.screens.starterScreens.registerScreen.components.StartTrackingButton
import com.example.habitat.presentation.screens.starterScreens.registerScreen.stepsScreens.FinishSetUpScreen
import com.example.habitat.presentation.screens.starterScreens.registerScreen.stepsScreens.Step2Screen
import kotlinx.coroutines.launch

private val pagerScrollAnimation: AnimationSpec<Float> = tween(
    durationMillis = 400
)

@Composable
fun RegisterMainScreen(
    navController: NavController,
    uiState: RegisterUiState,
    executeEvent: (RegisterEvent) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = 1
    ) { REGISTER_PAGE_COUNT + INITIAL_REGISTER_PAGE }

    val progressBarValue by remember(pagerState.currentPage) {
        mutableStateOf(pagerState.currentPage.toFloat() / REGISTER_PAGE_COUNT)
    }
    val animatedProgressBarValue by animateFloatAsState(
        targetValue = progressBarValue,
        animationSpec = tween(

        )
    )

    fun turnBack() {
        if(pagerState.currentPage == INITIAL_REGISTER_PAGE) {
            navController.navigateUp()
        } else {
            scope.launch {
                pagerState.animateScrollToPage(
                    page = pagerState.currentPage - 1,
                    animationSpec = pagerScrollAnimation,
                )
            }
        }
    }

    BackHandler {
        turnBack()
    }

    Column(
        modifier = Modifier
            .padding(horizontal = MaterialTheme.responsiveLayout.generalScreenWidthPadding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .padding(top = MaterialTheme.responsiveLayout.paddingSmall)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TurnBackButton(
                turnBack = {
                    turnBack()
                }
            )

            Text(
                text = "${pagerState.currentPage}/$REGISTER_PAGE_COUNT",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }


        LinearProgressIndicator(
            modifier = Modifier
                .padding(top = MaterialTheme.responsiveLayout.paddingMedium)
                .fillMaxWidth()
                .height(MaterialTheme.responsiveLayout.progressIndicatorBarHeight)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary)
            ,
            progress = { animatedProgressBarValue },
            trackColor = MaterialTheme.colorScheme.secondary,
            strokeCap = StrokeCap.Round,
            gapSize = 0.dp,
            drawStopIndicator = {}
        )

        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = pagerState,
            userScrollEnabled = false
        ) { page ->
            when (page) {
                1 -> {
                    Step1screen(
                        nameInput = uiState.userName,
                        ageInput = if(uiState.userAge == 0) "" else uiState.userAge.toString(),
                        livePlaceInput = uiState.userLivePlace,
                        executeEvent = executeEvent
                    )
                }
                2 -> Step2Screen(
                    selectedHabits = uiState.selectedHabits,
                    executeEvent = executeEvent,
                )
                3 -> {
                    FinishSetUpScreen()
                }
            }
        }

        AnimatedVisibility(
            visible = pagerState.currentPage == REGISTER_PAGE_COUNT
        ) {
            StartTrackingButton(
                modifier = Modifier
                    .padding(bottom = MaterialTheme.responsiveLayout.paddingMedium)
                    .height(MaterialTheme.responsiveLayout.buttonHeight1)
                    .fillMaxWidth(),
                onClick = {
                    executeEvent(RegisterEvent.CompleteRegistration)
                    navController.navigate(ScreensRoutes.Home.route) {
                        popUpTo(ScreensRoutes.GetStarted.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        AnimatedVisibility(
            visible = pagerState.currentPage != REGISTER_PAGE_COUNT
        ) {
            SkipProceedButtons(
                modifier = Modifier
                    .padding(bottom = MaterialTheme.responsiveLayout.paddingMedium)
                    .fillMaxWidth(),
                onSkip = {
                    scope.launch {
                        pagerState.animateScrollToPage(
                            page = pagerState.currentPage + 1,
                            animationSpec = pagerScrollAnimation,
                        )
                    }
                },
                onProceed = {

                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EnterUserDataScreenPreview() {
    HabitatTheme {
       RegisterMainScreen(
           navController = rememberNavController(),
           uiState = RegisterUiState(),
           executeEvent = {}
           )
    }
}