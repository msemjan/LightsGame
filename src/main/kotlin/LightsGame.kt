import java.awt.Rectangle
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.SwingUtilities


class LightsGame(numLights: Int) {
    private
    val BUTTON_SIZE_X = 45
    val BUTTON_SIZE_Y = 45
    val BUTTON_OFFSET_X = 10
    val BUTTON_OFFSET_Y = 10
    val n = numLights
    var playing = true
    var lights = Array(n) { BooleanArray(n) }
    var buttons = MutableList(n * n) { JButton(" ") }
    var frame = JFrame("Lights: The Game")

    init {
        SwingUtilities.invokeLater {
            frame.title = "Lights: The Game"
            frame.layout = null
            frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

            frame.setSize((n + 1) * BUTTON_SIZE_X, (n + 2) * BUTTON_SIZE_Y)

            for (i in 0 until n) {
                for (j in 0 until n) {
                    buttons[i * n + j].addActionListener { _ ->
                        this.switchLightAndNeighbors(i, j)
                    }
                    buttons[i * n + j].bounds = Rectangle(i * BUTTON_SIZE_X + BUTTON_OFFSET_X,
                        j * BUTTON_SIZE_Y + BUTTON_OFFSET_Y,
                        BUTTON_SIZE_X,
                        BUTTON_SIZE_Y)
                    frame.add(buttons[i * n + j])
                }
            }
            frame.isVisible = true
            frame.setLocationRelativeTo(null)
        }
    }

    fun reset(){
        for (i in 0 until n) {
            for (j in 0 until n) {
                buttons[i * n + j].text = " "
                lights[i][j] = false
            }
        }
        playing = true
    }

    fun isPlaying(): Boolean{
        return playing
    }

    fun winning(): Boolean{
        var test = true
        for(row in lights){
            test = row.all { x -> x }
            if(!test){
                break
            }
        }

        if(test)
            JOptionPane.showMessageDialog(null, "You have won the game!")

        return test
    }

    fun switchLight(x: Int, y: Int){
        if( (x in 0 until n) && (y in 0 until n)){
            lights[x][y] = !lights[x][y]
            buttons[x * n + y].text = if (lights[x][y]) "⭐️" else " "
        }
    }

    fun switchLightAndNeighbors(x: Int, y: Int){
        switchLight(x, y)
        switchLight(x + 1, y)
        switchLight(x - 1, y)
        switchLight(x, y + 1)
        switchLight(x, y - 1)
        if(winning()){
            playing = false
            val dialogButton = JOptionPane.showConfirmDialog(null, "Reset the game?", "Reset?", JOptionPane.YES_NO_OPTION)
            if(dialogButton==JOptionPane.YES_OPTION){
                reset()
            }else System.exit(0)
        }
    }


}

fun main() {
    val n: Int = JOptionPane.showInputDialog("Size of the board?", 3).toInt()
    var game: LightsGame = LightsGame(n)
}
