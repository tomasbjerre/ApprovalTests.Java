package org.approvaltests.machine_specific_tests.reporters;

import java.io.File;

import org.approvaltests.Approvals;
import org.approvaltests.machine_specific_tests.MachineSpecificTest;
import org.approvaltests.namer.NamedEnvironment;
import org.approvaltests.namer.NamerFactory;
import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.QueryableDiffReporterHarness;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.macosx.DiffMergeReporter;
import org.approvaltests.reporters.macosx.MacDiffReporter;
import org.approvaltests.reporters.windows.TortoiseTextDiffReporter;
import org.approvaltests.reporters.windows.WinMergeReporter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.spun.util.ClassUtils;

public class GenericDiffReporterTest extends MachineSpecificTest
{
  @UseReporter(DiffMergeReporter.class)
  @Test
  public void testGetWorkingReportersForEnvironment() {
    try (NamedEnvironment ne = NamerFactory.asMachineNameSpecificTest())
    {
      Approvals.verifyAll("reporters", MacDiffReporter.INSTANCE.getWorkingReportersForEnviroment());
    }
  }
  @Disabled("requires windows and TortoiseDiff installed")
  @Test
  public void testTortoiseDiff() {
    approveGenericReporter("a.txt", "b.txt", new TortoiseTextDiffReporter());
  }
  @Disabled("requires windows and WinMerge installed")
  @Test
  public void testWinMerge() {
    approveGenericReporter("a.txt", "b.txt", new WinMergeReporter());
  }
  private void approveGenericReporter(String a, String b, GenericDiffReporter reporter) {
    File directory = ClassUtils.getSourceDirectory(getClass());
    String aPath = directory.getAbsolutePath() + File.separator + a;
    String bPath = directory.getAbsolutePath() + File.separator + b;
    Approvals.verify(new QueryableDiffReporterHarness(reporter, aPath, bPath));
  }
}