/*
 * Copyright 2019 Google LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.tools.opensource.dependencies;

import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import org.eclipse.aether.util.artifact.JavaScopes;

/**
 * A node in Maven dependency graph.
 */
@AutoValue
public abstract class DependencyGraphEdge {

  public abstract boolean isOptional();

  public abstract Scope getScope();

  private static Builder builder() {
    return new AutoValue_DependencyGraphEdge.Builder();
  }

  static DependencyGraphEdge create(boolean isOptional, String scope) {
    return builder().setOptional(isOptional).setScope(
        Preconditions.checkNotNull(Scope.fromString(scope))).build();
  }

  @AutoValue.Builder
  abstract static class Builder {

    abstract Builder setOptional(boolean flag);

    abstract Builder setScope(Scope scope);

    abstract DependencyGraphEdge build();
  }


  /**
   * Scope of Maven dependency.
   */
  enum Scope {
    COMPILE,
    PROVIDED,
    SYSTEM,
    RUNTIME,
    TEST;

    /**
     * Returns {@link Scope} from {@link String}. Null if the value does not match any of the Maven
     * scope
     */
    static Scope fromString(String scope) {
      switch (scope) {
        case JavaScopes.COMPILE:
          return COMPILE;
        case JavaScopes.PROVIDED:
          return PROVIDED;
        case JavaScopes.SYSTEM:
          return SYSTEM;
        case JavaScopes.RUNTIME:
          return RUNTIME;
        case JavaScopes.TEST:
          return TEST;
        default:
          return null;
      }
    }
  }
}
