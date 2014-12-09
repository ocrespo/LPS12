package es.ucm.fdi.lps.p6.test.data.action.active;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AttackerAllStatsModifiersTest.class,
		ChangeAllEntityElementsTest.class, ChangeEntityTest.class,
		ChangeTest.class, CounterTest.class, DamageAttackStatTest.class,
		DefenderAllStatsModifiersTest.class,
		MultiplyStatsModifiersTest.class, TurnTest.class,
		UnStopActiveTest.class })
public class AllTestsActive {

}
