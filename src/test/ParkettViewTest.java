package test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import business.kunde.Sw;
import gui.parkett.ParkettView;
import javafx.application.Platform;
import javafx.stage.Stage;

public class ParkettViewTest {

	@BeforeAll
	static void initJavaFx() throws Exception {
		CountDownLatch latch = new CountDownLatch(1);
		Platform.startup(latch::countDown);
		latch.await();
	}

	private static void runFxAndWait(Runnable action) throws Exception {
		CountDownLatch latch = new CountDownLatch(1);
		Platform.runLater(() -> {
			try {
				action.run();
			} finally {
				latch.countDown();
			}
		});
		latch.await();
	}

	@Test
	void ausgewaehlteParkettSonderwuenscheWerdenAngezeigt() throws Exception {
		AtomicReference<boolean[]> actualRef = new AtomicReference<>();

		runFxAndWait(() -> {
			Stage dummyStage = new Stage();
			ParkettView parkettView = new ParkettView(null, dummyStage);

			int[][] mockSw = { { Sw.LHD_M_ESS_EG.id, 1 }, { Sw.SP_OG.id, 1 }, { Sw.SP_OHNE_BAD_DG.id, 1 } };

			parkettView.updateSwInView(mockSw);
			actualRef.set(parkettView.holeIsSelectedFuerCheckboxen());
		});

		boolean[] expected = { true, // LHD_M_ESS_EG - 0
				false, // LHD_M_KUECHE_EG -1
				false, // SP_ESS_EG - 2
				false, // SP_KUECHE_EG -3
				false, // LHD_M_OG - 4
				true, // SP_OG -5
				false, // LHD_M_KOMPLETT_DG-6
				false, // LDH_M_OHNE_BAD_DG - 7
				false, // SP_KOMPLETT_DG - 8
				true // SP_OHNE_BAD_DG - 9
		};

		assertArrayEquals(expected, actualRef.get());
	}

	@Test
	void keineSonderwuenscheAllesFalse() throws Exception {
		AtomicReference<boolean[]> actualRef = new AtomicReference<>();

		runFxAndWait(() -> {
			Stage dummyStage = new Stage();
			ParkettView parkettView = new ParkettView(null, dummyStage);

			parkettView.updateSwInView(null);
			actualRef.set(parkettView.holeIsSelectedFuerCheckboxen());
		});

		boolean[] expected = { false, false, false, false, false, false, false, false, false, false };
		assertArrayEquals(expected, actualRef.get());
	}
}
