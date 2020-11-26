package com.trivago.vo;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.filters.FilterPackageInfo;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.impl.NoPublicFieldsExceptStaticFinalRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Test;

import java.util.List;

public class PojoTest {
    private static final int EXPECTED_CLASS_COUNT = 5;
    private static final String POJO_PACKAGE = "com.trivago.vo";

    @Test
    public void ensureExpectedPojoCount() {
        List<PojoClass> pojoClasses = PojoClassFactory.getPojoClasses(
                POJO_PACKAGE,
                pojoClass -> !pojoClass.getSourcePath().contains("/test-classes/")
        );
        Affirm.affirmEquals("Classes added / removed?", EXPECTED_CLASS_COUNT, pojoClasses.size());
    }

    @Test
    public void testPojoStructureAndBehavior() {
        Validator validator = ValidatorBuilder.create()
                                              .with(new SetterTester())
                                              .with(new GetterTester())
                                              .with(new NoPublicFieldsExceptStaticFinalRule())
                                              .build();

        validator.validate(POJO_PACKAGE, new FilterPackageInfo());
    }
}

