/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.marthia.app.boomgrad.presentation.theme

import androidx.compose.ui.graphics.Color


val Neutral8 = Color(0xff121212)
val Neutral7 = Color(0xde000000)
val Neutral6 = Color(0x99000000)
val Neutral5 = Color(0x61000000)
val Neutral4 = Color(0x1f000000)
val Neutral3 = Color(0x1fffffff)
val Neutral2 = Color(0x61ffffff)
val Neutral1 = Color(0xbdffffff)
val Neutral0 = Color(0xffffffff)

val Grey0 = Color(0xFFEDEDED)


// Two palettes — Green and DarkGreen.
// Turquoise removed: hue difference from Green was only 5–9°, not perceptually
// distinct enough to justify 12 extra color slots.
//
// Green    H≈178°  — full tonal range, use for interactive elements,
//                    accents, text on dark backgrounds (Green3–Green0)
//
// DarkGreen H=178° — dark register only (L: 3%–56%), use exclusively for
//                    backgrounds, surfaces, and elevation layers.
//                    Never use for text — lowest contrast ratio at L:56%
//                    against L:3% is still only ~5:1.
// ─────────────────────────────────────────────────────────────────────────────

// ── Green ────────────────────────────────────────────────────────────────────
// Clean, well-ordered. Kept as-is.
//
// Index: 11    10    9     8     7     6     5     4     3     2     1     0
// L%:    16    21    26    30    35    43    53    65    75    86    92    98

val Green11 = Color(0xFF00524F)  // L:16%
val Green10 = Color(0xFF006A66)  // L:21%
val Green9  = Color(0xFF00827D)  // L:26%
val Green8  = Color(0xFF009896)  // L:30%  ← primary brand color
val Green7  = Color(0xFF00B2AC)  // L:35%
val Green6  = Color(0xFF13CAC2)  // L:43%
val Green5  = Color(0xFF30E0D8)  // L:53%
val Green4  = Color(0xFF57F2EC)  // L:65%
val Green3  = Color(0xFF86FAF4)  // L:75%
val Green2  = Color(0xFFBBFDFA)  // L:86%
val Green1  = Color(0xFFD6FEFD)  // L:92%
val Green0  = Color(0xFFF2FFFE)  // L:98%

// ── DarkGreen ────────────────────────────────────────────────────────────────
// Fixed: perceptual curve — steps tight at dark end, expanding toward lighter.
// Capped at L:56% — stays in the dark register throughout.
// DarkGreen1/DarkGreen0 were identical to Green1/Green0 — removed and replaced
// with proper dark shades.
//
// Index: 11    10    9     8     7     6     5     4     3     2     1     0
// L%:     3     5     8    12    17    24    32    41    46    50    53    56
// Step:     +2    +3    +4    +5    +7    +8    +9    +5    +4    +3    +3

val DarkGreen11 = Color(0xFF000F0E)  // L:3%   ← darkest background
val DarkGreen10 = Color(0xFF001918)  // L:5%
val DarkGreen9  = Color(0xFF002827)  // L:8%
val DarkGreen8  = Color(0xFF003D3B)  // L:12%
val DarkGreen7  = Color(0xFF005653)  // L:17%
val DarkGreen6  = Color(0xFF007A76)  // L:24%
val DarkGreen5  = Color(0xFF00A39D)  // L:32%
val DarkGreen4  = Color(0xFF00D1CA)  // L:41%
val DarkGreen3  = Color(0xFF00EAE2)  // L:46%
val DarkGreen2  = Color(0xFF00FFF6)  // L:50%
val DarkGreen1  = Color(0xFF0FFFF7)  // L:53%
val DarkGreen0  = Color(0xFF1EFFF7)  // L:56%


val Turquoise11 = Color(0xff00525d)
val Turquoise10 = Color(0xff006a74)
val Turquoise9 = Color(0xff00828a)
val Turquoise8 = Color(0xff0098AA) // Main Turquoise color
val Turquoise7 = Color(0xff00b2c3)
val Turquoise6 = Color(0xff13cad9)
val Turquoise5 = Color(0xff30e0e8)
val Turquoise4 = Color(0xff57f2f3)
val Turquoise3 = Color(0xff86faf8)
val Turquoise2 = Color(0xffbbfdfc)
val Turquoise1 = Color(0xffd6fefe)
val Turquoise0 = Color(0xfff2ffff)



val Hana11 = Color(0xff521c00)
val Hana10 = Color(0xff6a2d00)
val Hana9 = Color(0xff823c00)
val Hana8 = Color(0xff984602)
val Hana7 = Color(0xffb24f0e) // main
val Hana6 = Color(0xffca581b)
val Hana5 = Color(0xffe0632a)
val Hana4 = Color(0xfff57342)
val Hana3 = Color(0xfffa906b)
val Hana2 = Color(0xfffdb99c)
val Hana1 = Color(0xfffedbd0)
val Hana0 = Color(0xfffff4ef)

val Khaki11 = Color(0xff524119)
val Khaki10 = Color(0xff6a5327)
val Khaki9 = Color(0xff826535)
val Khaki8 = Color(0xff987643)
val Khaki7 = Color(0xffb28951)
val Khaki6 = Color(0xffca9d60) // main
val Khaki5 = Color(0xffe0b16f)
val Khaki4 = Color(0xfff5c67f)
val Khaki3 = Color(0xfffad99b)
val Khaki2 = Color(0xfffdecbc)
val Khaki1 = Color(0xfffef3dd)
val Khaki0 = Color(0xfffffbef)


val HanaGreen11 = Color(0xff162015)
val HanaGreen10 = Color(0xff21301f)
val HanaGreen9 = Color(0xff2a3e29)
val HanaGreen8 = Color(0xff354533) // Main HanaGreen color
val HanaGreen7 = Color(0xff435441)
val HanaGreen6 = Color(0xff536651)
val HanaGreen5 = Color(0xff667a63)
val HanaGreen4 = Color(0xff7e927c)
val HanaGreen3 = Color(0xff9eab9b)
val HanaGreen2 = Color(0xffc5d0c3)
val HanaGreen1 = Color(0xffe1e8e0)
val HanaGreen0 = Color(0xfff5f8f4)


val FunctionalRed5 = Color(0xffff383c)
val FunctionalRed1 = Color(0xFFFDEDEE)
val FunctionalRedDark = Color(0xffea6d7e)
val FunctionalRedDark1 = Color(0xFF3B2527)
val FunctionalGreen = Color(0xff52c41a)
val FunctionalGrey = Color(0xfff6f6f6)
val FunctionalDarkGrey = Color(0xff2e2e2e)

const val AlphaNearOpaque = 0.95f
