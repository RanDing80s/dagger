/*
 * Copyright (C) 2015 The Dagger Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dagger.functional.cycle;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.TruthJUnit.assume;

import dagger.functional.cycle.LongCycle.LongCycleComponent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class LongCycleTest {

  /**
   * Tests a cycle long enough that the real factory is created in a separate initialize method from
   * the delegate factory.
   */
  @Test
  public void longCycle() {
    LongCycleComponent longCycleComponent = DaggerLongCycle_LongCycleComponent.create();
    assertThat(longCycleComponent.class1()).isNotNull();
  }

  /**
   * Fails if {@link LongCycleComponent} doesn't have a long enough cycle to make sure the real
   * factory is created in a separate method from the delegate factory.
   */
  @Test
  public void longCycleHasMoreThanOneInitializeMethod() throws NoSuchMethodException {
    assume().that(System.getProperty("dagger.mode")).isNotEqualTo("FastInit");
    assume()
        .that(System.getProperty("dagger.mode"))
        .isNotEqualTo("FastInitAndAheadOfTimeSubcomponents");
    DaggerLongCycle_LongCycleComponent.class
        .getDeclaredMethod("initialize2", DaggerLongCycle_LongCycleComponent.Builder.class);
  }
}
