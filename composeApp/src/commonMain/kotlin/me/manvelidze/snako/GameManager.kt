package me.manvelidze.snako

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import me.manvelidze.snako.game.Direction
import me.manvelidze.snako.game.ParticleType
import me.manvelidze.snako.game.Position

@Stable
class GameManager(private val screenWidth: Int, private val screenHeight: Int) {

    var particles by mutableStateOf<List<Position>>(listOf())
        private set

    var applePosition by mutableStateOf(Position.random(screenWidth, screenHeight))
        private set

    var isScreenActive: Boolean = false

    private var direction = Direction.entries.random()

    init {
        reset()
        CoroutineScope(Dispatchers.Main).launch {
            launch {
                while (isActive) {
                    move(applePosition) { particleType ->
                        when (particleType) {
                            ParticleType.Border, ParticleType.Self -> {
                                reset()
                            }

                            ParticleType.Apple -> addParticle()
                        }
                        applePosition = Position.random(screenWidth, screenHeight)
                    }
                    delay(200L)
                }
            }
        }
    }

    fun onDirectionChange(direction: Direction) {
        if (direction.reverse == this.direction.ordinal && particles.size > 1) return
        else this.direction = direction
        println("changing direction to $direction")
    }

    private fun move(applePos: Position, onIntersect: (ParticleType) -> Unit) {
        if (isScreenActive.not()) return
        val diff = getDiff()
        var newHeadPos: Position? = null
        particles = particles.mapIndexed { index, position ->
            if (index == 0) {
                Position(
                    position.x + diff.first,
                    position.y + diff.second
                ).also { newHeadPos = it }
            } else {
                particles[index - 1].also {
                    if (it.intersects(newHeadPos!!)) {
                        onIntersect.invoke(ParticleType.Self)
                        return
                    }
                }
            }
        }
        val head = particles.first()
        if (head.intersects(applePos)) {
            onIntersect.invoke(ParticleType.Apple)
            return
        }
        if ((head.x < 0 || head.x > screenWidth) || (head.y < 0 || head.y > screenHeight)) {
            onIntersect.invoke(ParticleType.Border)
        }

    }

    private fun addParticle() {
        val lastParticle = particles.last()
        val diff = getDiff()
        val newPos = Position(lastParticle.x + diff.first, lastParticle.y + diff.second)
        particles = particles.plus(newPos)
    }

    private fun reset() {
        particles = listOf(Position(screenWidth / 2, screenHeight / 2))
        direction = Direction.entries.random()
    }

    private fun getDiff(): Pair<Int, Int> {
        return when (direction) {
            Direction.Top -> 0 to -20
            Direction.Bottom -> 0 to 20
            Direction.Left -> -20 to 0
            Direction.Right -> 20 to 0
        }
    }
}