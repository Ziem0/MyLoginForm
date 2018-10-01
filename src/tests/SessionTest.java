import com.ziemo.login.model.Server;
import com.ziemo.login.model.Session;
import com.ziemo.login.view.WebDisplay;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SessionTest {

	private Session session;

	@Mock
	private WebDisplay display = mock(WebDisplay.class);

	@BeforeEach
	void setUp() {
		session = new Session("123");
		Mockito.when(display.createLoginPage(null)).thenReturn("ok!");
	}

	@Test
	void testIfMockitoWorks() {
		assertEquals("ok!",display.createLoginPage(null));
	}

	@Test
	void ifConstructorOk() {
		Assumptions.assumingThat(session.getSessionId() != null, () -> {
			assertAll(
					() -> assertEquals(1, session.getDuration()),
					() -> assertEquals(2, session.getDuration() + 1)
			);
			assertAll(
					() -> assertNotNull(session.getExpire()),
					() -> assertEquals(1, session.getDuration()),
					() -> {
						int start = 12;
						assertEquals("123", session.getSessionId());
					}
			);
		});
	}

	@ParameterizedTest
	@ValueSource(ints = {1, 2, 3, 4, 5, 6, 67, 7, 8})
	void testCorrectId(int value) {
		Session session = new Session(String.valueOf(value));
		assertEquals(String.valueOf(value), session.getSessionId());
	}

	@ParameterizedTest
	@MethodSource("intStream")
	void name(int value) {
		Session session = new Session(String.valueOf(value));
		assertEquals(String.valueOf(value), session.getSessionId());
	}

	private static IntStream intStream() {
		return IntStream.range(1, 20);
	}

	@TestFactory
	Stream<DynamicTest> complex() {
		Integer[] counter = new Integer[]{0, 1, 2};
		String[] names = new String[]{"ziemo", "ania", "nina"};

		return Arrays.stream(counter).map(number -> DynamicTest.dynamicTest("test for name: " + number, () -> {
			String name = names[number];
			assertEquals(names[number], new Session(name).getSessionId());
		}));
	}

	@Test
	void testThrowExceptionWhenIncorrectId() {
		assertDoesNotThrow(() -> {
			String count = "asd";
			new Session(count);
		});
	}


}



