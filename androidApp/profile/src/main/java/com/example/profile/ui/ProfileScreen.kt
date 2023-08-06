package com.example.profile.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.core.component.ui.compose.RoundedImageView
import com.example.core.theme.CleanArchTheme
import com.example.profile.R
import com.example.core.R as R1


@OptIn(ExperimentalMaterialApi::class, ExperimentalMotionApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    navController: NavController? = null,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    viewModel.getProfileInfo()

    val profile by viewModel.profileState.collectAsStateWithLifecycle()

    if (profile == null) return

    CleanArchTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
        ) {
            val swipingState = rememberSwipeableState(initialValue = SwipingStates.EXPANDED)
            BoxWithConstraints(//to get the max height
                modifier = Modifier.fillMaxSize()
            ) {
                val heightInPx = with(LocalDensity.current) {
                    maxHeight.toPx()
                }
                val nestedScrollConnection = remember {
                    object : NestedScrollConnection {
                        override fun onPreScroll(
                            available: Offset,
                            source: NestedScrollSource,
                        ): Offset {
                            val delta = available.y
                            return if (delta < 0) {
                                swipingState.performDrag(delta).toOffset()
                            } else {
                                Offset.Zero
                            }
                        }

                        override fun onPostScroll(
                            consumed: Offset,
                            available: Offset,
                            source: NestedScrollSource,
                        ): Offset {
                            val delta = available.y
                            return swipingState.performDrag(delta).toOffset()
                        }

                        override suspend fun onPostFling(
                            consumed: Velocity,
                            available: Velocity,
                        ): Velocity {
                            swipingState.performFling(velocity = available.y)
                            return super.onPostFling(consumed, available)
                        }

                        private fun Float.toOffset() = Offset(0f, this)
                    }
                }

                Box(//root container
                    modifier = Modifier
                        .fillMaxSize()
                        .swipeable(
                            state = swipingState, thresholds = { _, _ ->
                                FractionalThreshold(0.05f)//it can be 0.5 in general
                            }, orientation = Orientation.Vertical, anchors = mapOf(
                                0f to SwipingStates.COLLAPSED,//min height is collapsed
                                heightInPx to SwipingStates.EXPANDED,//max height is expanded
                            )
                        )
                        .nestedScroll(nestedScrollConnection)
                ) {
                    val computedProgress by remember {//progress value will be decided as par state
                        derivedStateOf {
                            if (swipingState.progress.to == SwipingStates.COLLAPSED) swipingState.progress.fraction
                            else 1f - swipingState.progress.fraction
                        }
                    }
                    val startHeightNum = 300
                    MotionLayout(
                        modifier = Modifier.fillMaxSize(),
                        start = ConstraintSet {
                            val header = createRefFor("header")
                            val headerContent = createRefFor("header-content")
                            val body = createRefFor("body")
                            val content1 = createRefFor("content1")
                            val content2 = createRefFor("content2")
                            constrain(header) {
                                this.width = Dimension.matchParent
                                this.height = Dimension.value(300.dp)
                            }
                            constrain(headerContent) {
                                this.alpha = 1f
                                this.height = Dimension.value(300.dp)
                            }
                            constrain(body) {
                                this.width = Dimension.matchParent
                                this.height = Dimension.fillToConstraints
                                this.top.linkTo(header.bottom, 0.dp)
                                this.bottom.linkTo(parent.bottom, 0.dp)
                            }
                            constrain(content1) {
                                this.start.linkTo(header.start)
                                this.end.linkTo(header.end)
                                this.bottom.linkTo(content2.top, 8.dp)
                                this.height = Dimension.value(128.dp)
                            }
                            constrain(content2) {
                                this.start.linkTo(header.start)
                                this.end.linkTo(header.end)
                                this.bottom.linkTo(header.bottom, 24.dp)
                            }
                        },
                        end = ConstraintSet {
                            val header = createRefFor("header")
                            val headerContent = createRefFor("header-content")
                            val body = createRefFor("body")
                            val content1 = createRefFor("content1")
                            val content2 = createRefFor("content2")
                            constrain(header) {
                                this.height = Dimension.value(60.dp)
                            }
                            constrain(headerContent) {
                                this.height = Dimension.value(60.dp)
                                this.alpha = 0f
                            }
                            constrain(body) {
                                this.width = Dimension.matchParent
                                this.height = Dimension.fillToConstraints
                                this.top.linkTo(header.bottom, 0.dp)
                                this.bottom.linkTo(parent.bottom, 0.dp)
                            }
                            constrain(content1) {
                                this.start.linkTo(header.start, 24.dp)
                                this.top.linkTo(header.top, 8.dp)
                                this.bottom.linkTo(header.bottom, 8.dp)
                                this.height = Dimension.fillToConstraints
                            }
                            constrain(content2) {
                                this.start.linkTo(content1.end, 12.dp)
                                this.bottom.linkTo(header.bottom)
                                this.top.linkTo(header.top)
                            }
                        },
                        progress = computedProgress,
                    ) {

                        Box(
                            modifier = Modifier
                                .layoutId("body")
                                .fillMaxWidth()
                                .background(Color.White)
                        ) {
                            Column(modifier = Modifier.padding(20.dp, 25.dp, 20.dp, 12.dp)) {
                                Text(
                                    text = "Username",
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    profile!!.user.username, color = Color.Gray, fontSize = 14.sp
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = "Email",
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    profile!!.email ?: "undefined",
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = "Phone",
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    profile!!.phone ?: "undefined",
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .layoutId("header")
                                .fillMaxWidth()
                                .height(startHeightNum.dp)
                                .background(Color(0xFFFFFFFF))
                        )

                        Image(
                            painter = painterResource(id = R.drawable.cover),
                            contentDescription = "cover",
                            modifier = Modifier
                                .layoutId("header-content")
                                .fillMaxWidth()
                                .height(startHeightNum.dp),
                            contentScale = ContentScale.Crop
                        )

                        RoundedImageView(
                            modifier = Modifier.layoutId("content1"),
                            drawable = R1.drawable.pic,
                            contentDescription = "profile",
                            aspectRatio = 1f,
                            borderWidth = 1.dp,
                            borderColor = Color(0xFF673AB7),
                            borderPadding = 3.dp
                        )

                        Text(
                            profile!!.user.name ?: "",
                            color = Color.Black,
                            modifier = Modifier.layoutId("content2")
                        )
                    }
                }
            }
        }
    }
}

enum class SwipingStates {
    //our own enum class for stoppages e.g. expanded and collapsed
    EXPANDED, COLLAPSED
}
