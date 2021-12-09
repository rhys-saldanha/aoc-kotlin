import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test

internal class Day08Test {
    @Test
    internal fun `map file line to characters`() {
        val lines =
            getLines(listOf("acedgfb cdfbe | cdfeb fcadb"))

        assertThat(lines, hasSize(1))
        val line = lines[0]

        assertThat(line, hasSize(2))
        assertThat(
            line[0], equalTo(
                listOf(
                    "acedgfb".toCharArray().toSet(),
                    "cdfbe".toCharArray().toSet(),
                )
            )
        )
        assertThat(
            line[1], equalTo(
                listOf(
                    "cdfeb".toCharArray().toSet(),
                    "fcadb".toCharArray().toSet(),
                )
            )
        )
    }

    @Test
    internal fun `get output number`() {
        val lines =
            getLines(
                listOf(
                    "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | acedgfb cdfbe gcdfa fbcad dab",
                    "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cefabd cdfgeb eafb cagedb ab",
                )
            )

        assertThat(getOutputNumber(lines[0]), equalTo(85237))
        assertThat(getOutputNumber(lines[1]), equalTo(96401))
    }
}