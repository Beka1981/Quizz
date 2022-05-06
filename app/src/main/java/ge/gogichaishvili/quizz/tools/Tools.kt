package ge.gogichaishvili.quizz.tools

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer

object Tools {

    fun playSound(context: Context, audio: Int) {

        val mediaPlayer = MediaPlayer.create(context, audio)

        mediaPlayer.apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )

            mediaPlayer.isLooping = false

            mediaPlayer.setVolume(5f, 5f)

            mediaPlayer.setOnCompletionListener { player ->
                player.stop()
                player.release()
            }

            mediaPlayer.start()

        }

    }


    fun millisecondsConverter (milliseconds: Long) : String {

        val minutes = milliseconds / 1000 / 60
        val seconds = milliseconds / 1000 % 60

        println("$milliseconds Milliseconds = $minutes minutes and $seconds seconds.")
        return "$minutes:$seconds"
    }


}