# Stacked Funnel Chart

[![GitHub](https://img.shields.io/github/license/indramahkota/stacked-funnel-chart?color=blue)](https://github.com/indramahkota/stacked-funnel-chart/blob/master/LICENSE) [![GitHub stars](https://img.shields.io/github/stars/indramahkota/stacked-funnel-chart)](https://github.com/indramahkota/stacked-funnel-chart/stargazers) [![All Contributors](https://img.shields.io/badge/all_contributors-1-orange.svg?style=flat-square)](#contributors) [![Release](https://jitpack.io/v/indramahkota/stacked-funnel-chart.svg)](https://jitpack.io/#indramahkota/stacked-funnel-chart)

Custom View for display Stacked Funnel Chart.

## Installation

### Add dependencies:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}

dependencies {
    implementation("com.github.indramahkota:stacked-funnel-chart:0.0.7")
}
```

## Usage

```xml
<com.indramahkota.stackedfunnelchart.StackedFunnelChart
    android:id="@+id/stacked_funnel_chart"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:stacked_1_title="Product 1"
    app:stacked_2_title="Product 2"
    app:stacked_3_title="Product 3" />
```

## Preview

![Stacked Funnel Chart Preview](https://raw.githubusercontent.com/indramahkota/indramahkota.github.io/master/assets/githubs/stacked%20funnel%20chart.png)

## License

```markdown
Copyright (c) 2021 Indra Mahkota

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
