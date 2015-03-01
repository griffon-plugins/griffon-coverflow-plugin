/*
 * Copyright 2014-2015 the original author or authors.
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
package griffon.builder.swing

import griffon.builder.swing.factory.ImageFlowFactory
import griffon.builder.swing.factory.ImageFlowItemFactory
import griffon.plugins.coverflow.ui.GradientPanel
import griffon.plugins.coverflow.ui.StackLayout
import groovy.swing.factory.ComponentFactory
import groovy.swing.factory.LayoutFactory
import org.codehaus.griffon.runtime.groovy.view.AbstractBuilderCustomizer

import javax.inject.Named

/**
 * @author Andres Almiray
 */
@Named('coverflow-swing')
class CoverflowSwingBuilderCustomizer extends AbstractBuilderCustomizer {
    CoverflowSwingBuilderCustomizer() {
        setFactories([
            gradientPanel: new ComponentFactory(GradientPanel),
            stackLayout  : new LayoutFactory(StackLayout),
            imageFlow    : new ImageFlowFactory(),
            imageFlowItem: new ImageFlowItemFactory()
        ])
    }
}
