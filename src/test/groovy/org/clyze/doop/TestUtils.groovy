package org.clyze.doop

import org.clyze.analysis.Analysis
import org.clyze.doop.utils.SouffleScript

/**
 * Utility class with checker methods used by other tests.
 */
class TestUtils {
	static void relationHasApproxSize(Analysis analysis, String relation, int expectedSize) {
		int actualSize = 0
		File file = new File("${analysis.database}/${relation}.csv")
		BufferedReader br = new BufferedReader(new FileReader(file))
		br.withCloseable { while (it.readLine() != null) { actualSize++ } }

		// We expect numbers to deviate by 10%.
		assert actualSize > (expectedSize * 0.9)
		assert actualSize < (expectedSize * 1.1)
	}

	static void metricIsApprox(Analysis analysis, String metric, long expectedVal) {
		long actualVal = -1

		String metrics = "${analysis.database}/Stats_Metrics.csv"
		(new File(metrics)).eachLine { line ->
			String[] values = line.split('\t')
			if ((values.size() == 3) && (values[1] == metric)) {
				actualVal = values[2] as long
			}
		}
		// We expect numbers to deviate by 10%.
		assert actualVal > (expectedVal * 0.9)
		assert actualVal < (expectedVal * 1.1)
	}

	// Check that the binary generated by Souffle exists.
	static void execExists(Analysis analysis) {
		assert true == (new File("${analysis.outDir}/${SouffleScript.EXE_NAME}")).exists()
	}

	// Trivial check.
	static void metaExists(Analysis analysis) {
		assert true == (new File("${analysis.outDir}/meta")).exists()
	}
}
