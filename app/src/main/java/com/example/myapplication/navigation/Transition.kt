import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith

class Transition {

    enum class TransitionDirection {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT
    }

    companion object {
        private val toLeft = AnimatedContentTransitionScope.SlideDirection.Left
        private val toRight = AnimatedContentTransitionScope.SlideDirection.Right
        private const val DURATION = 500

        fun create(
            direction: TransitionDirection
        ): AnimatedContentTransitionScope<*>.() -> ContentTransform {
            val inDir = if (direction == TransitionDirection.LEFT_TO_RIGHT) toLeft else toRight
            val outDir = if (direction == TransitionDirection.RIGHT_TO_LEFT) toRight else toLeft

            return {
                slideIntoContainer(inDir, tween(DURATION)) togetherWith
                        slideOutOfContainer(outDir, tween(DURATION))
            }
        }
    }

}
