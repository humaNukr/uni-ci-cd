package kma;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("ua.edu.kma")
@IncludeTags("fast")
public class FastTestsSuite {
}