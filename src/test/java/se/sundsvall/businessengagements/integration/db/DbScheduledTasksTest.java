package se.sundsvall.businessengagements.integration.db;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class, SoftAssertionsExtension.class})
class DbScheduledTasksTest {

	@Mock
	private EngagementsCacheRepository mockRepository;

	private DbScheduledTasks scheduledTasks;

	@BeforeEach
	void setup() {
		scheduledTasks = new DbScheduledTasks(mockRepository, Duration.of(10L, ChronoUnit.SECONDS));
	}

	@Test
	void testRemoveEntity_whenTtlHasBeenPassed() {
		when(mockRepository.deleteAllByUpdatedBefore(any(LocalDateTime.class))).thenReturn(1);
		scheduledTasks.removeOldCacheEntries();
		verify(mockRepository, times(1)).deleteAllByUpdatedBefore(any(LocalDateTime.class));
	}

	@Test
	void testShouldNotRemoveEntity_whenTtlHasNotBeenPassed() {
		when(mockRepository.deleteAllByUpdatedBefore(any(LocalDateTime.class))).thenReturn(0);
		scheduledTasks.removeOldCacheEntries();
		verify(mockRepository, times(1)).deleteAllByUpdatedBefore(any(LocalDateTime.class));
	}

}
