package com.kalessil.phpStorm.phpInspectionsEA.api;

import com.intellij.codeInsight.intention.IntentionAction;
import com.jetbrains.php.config.PhpLanguageLevel;
import com.jetbrains.php.config.PhpProjectConfigurationFacade;
import com.kalessil.phpStorm.phpInspectionsEA.PhpCodeInsightFixtureTestCase;
import com.kalessil.phpStorm.phpInspectionsEA.inspectors.apiUsage.RandomApiMigrationInspector;

final public class RandomApiMigrationInspectorTest extends PhpCodeInsightFixtureTestCase {
    public void testIfFindsMtPatterns() {
        RandomApiMigrationInspector inspector = new RandomApiMigrationInspector();
        inspector.SUGGEST_USING_RANDOM_INT    = false;
        myFixture.enableInspections(inspector);

        myFixture.configureByFile("fixtures/api/random-api-mt.php");
        myFixture.testHighlighting(true, false, true);

        for (final IntentionAction fix : myFixture.getAllQuickFixes()) {
            myFixture.launchAction(fix);
        }
        myFixture.setTestDataPath(".");
        myFixture.checkResultByFile("fixtures/api/random-api-mt.fixed.php");
    }

    public void testIfFindsEdgePatterns() {
        PhpProjectConfigurationFacade.getInstance(myFixture.getProject()).setLanguageLevel(PhpLanguageLevel.PHP710);
        myFixture.enableInspections(new RandomApiMigrationInspector());
        myFixture.configureByFile("fixtures/api/random-api-edge.php");
        myFixture.testHighlighting(true, false, true);

        for (final IntentionAction fix : myFixture.getAllQuickFixes()) {
            myFixture.launchAction(fix);
        }
        myFixture.setTestDataPath(".");
        myFixture.checkResultByFile("fixtures/api/random-api-edge.fixed.php");
    }
}
