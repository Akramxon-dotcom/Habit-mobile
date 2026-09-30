package com.example.ui.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.ScriptSampleAudio
import com.example.data.model.ScriptSampleData
import com.example.ui.theme.HabitCardBg
import com.example.ui.theme.HabitCardElevated
import com.example.ui.theme.HabitDarkBg
import com.example.ui.theme.HabitGold
import com.example.ui.theme.HabitInk
import com.example.ui.theme.HabitInkSoft
import com.example.ui.theme.HabitSage

fun getScriptCategoryIcon(category: String): String = when (category) {
    "Detective & Mystery" -> "🕵️"
    "Technology & AI" -> "🤖"
    "Psychology & Mindset" -> "🧠"
    "Travel & Adventure" -> "✈️"
    "Business & Career" -> "💼"
    "Nature & Wildlife" -> "🌿"
    "History & Legends" -> "🏛️"
    "Daily Life & Dialogues" -> "☕"
    "Motivation & Discipline" -> "🔥"
    "Cosmos & Science" -> "🚀"
    else -> "📚"
}

/**
 * 📚 ScriptLibraryGridCard:
 * Individual modern grid card for a pre-loaded story script.
 * Displays title, category, level, duration, excerpt, and a prominent 'Read/Listen' action button.
 */
@Composable
fun ScriptLibraryGridCard(
    sample: ScriptSampleAudio,
    onReadListenClick: () -> Unit,
    onPlayInArena: (() -> Unit)? = null,
    onOpenInEditor: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val categoryIcon = getScriptCategoryIcon(sample.category)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onReadListenClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = HabitCardBg),
        border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.28f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Row 1: Category Tag & Duration Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = HabitGold.copy(alpha = 0.15f),
                    border = BorderStroke(0.5.dp, HabitGold.copy(alpha = 0.35f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(categoryIcon, fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = sample.category.take(16),
                            color = HabitGold,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = HabitDarkBg
                ) {
                    Text(
                        text = "⏱️ ${sample.durationSec}s",
                        color = HabitInkSoft,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Clear, Prominent Title
            Text(
                text = sample.title,
                color = HabitInk,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Proficiency Level & Narrator
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = HabitSage.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = sample.level,
                        color = HabitSage,
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                    )
                }
                Text(
                    text = "• ${sample.teacherName.take(18)}",
                    color = HabitInkSoft,
                    fontSize = 10.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Short Story Synopsis / Description
            Text(
                text = sample.descriptionUz,
                color = HabitInkSoft,
                fontSize = 11.sp,
                lineHeight = 15.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Prominent 'Read/Listen' Action Button for Immediate Access
            Button(
                onClick = onReadListenClick,
                colors = ButtonDefaults.buttonColors(containerColor = HabitGold),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Hearing,
                    contentDescription = null,
                    tint = HabitDarkBg,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Read / Listen",
                    color = HabitDarkBg,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Quick Shortcut Row (Arena & Editor)
            if (onPlayInArena != null || onOpenInEditor != null) {
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (onPlayInArena != null) {
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onPlayInArena() },
                            shape = RoundedCornerShape(8.dp),
                            color = HabitDarkBg,
                            border = BorderStroke(0.8.dp, HabitGold.copy(alpha = 0.3f))
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("🎮 Arena", color = HabitGold, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }

                    if (onOpenInEditor != null) {
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onOpenInEditor() },
                            shape = RoundedCornerShape(8.dp),
                            color = HabitDarkBg,
                            border = BorderStroke(0.8.dp, HabitGold.copy(alpha = 0.3f))
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("📝 Diktant", color = HabitGold, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 📖 ScriptReadListenDialog:
 * Immediate interactive modal that appears when the user clicks 'Read/Listen'.
 * Provides real-time TTS audio listening with Play/Pause, speed adjustment,
 * full story text display, and one-click routing to Arena or Dictation mode.
 */
@Composable
fun ScriptReadListenDialog(
    sample: ScriptSampleAudio,
    ttsEngine: TextToSpeech?,
    isTtsReady: Boolean,
    onDismiss: () -> Unit,
    onPlayInArena: () -> Unit,
    onOpenInEditor: () -> Unit
) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }
    var speechSpeed by remember { mutableFloatStateOf(1.0f) }

    // Start playing immediately when opened
    DisposableEffect(sample.id) {
        if (ttsEngine != null && isTtsReady) {
            ttsEngine.setSpeechRate(speechSpeed)
            ttsEngine.speak(sample.hiddenSpokenContent, TextToSpeech.QUEUE_FLUSH, null, "read_listen_${sample.id}")
            isPlaying = true
        }
        onDispose {
            ttsEngine?.stop()
        }
    }

    Dialog(
        onDismissRequest = {
            ttsEngine?.stop()
            onDismiss()
        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .fillMaxHeight(0.85f),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = HabitCardBg),
            border = BorderStroke(1.5.dp, HabitGold)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Top Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(getScriptCategoryIcon(sample.category), fontSize = 22.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = sample.title,
                                color = HabitInk,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(sample.category, color = HabitGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                Text("• ${sample.level}", color = HabitSage, fontSize = 11.sp)
                            }
                        }
                    }

                    IconButton(
                        onClick = {
                            ttsEngine?.stop()
                            onDismiss()
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Yopish", tint = HabitInkSoft)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Integrated Audio Player Controls
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = HabitDarkBg),
                    border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.35f))
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(
                                    onClick = {
                                        if (ttsEngine != null && isTtsReady) {
                                            if (isPlaying) {
                                                ttsEngine.stop()
                                                isPlaying = false
                                            } else {
                                                ttsEngine.setSpeechRate(speechSpeed)
                                                ttsEngine.speak(sample.hiddenSpokenContent, TextToSpeech.QUEUE_FLUSH, null, "read_listen_${sample.id}")
                                                isPlaying = true
                                            }
                                        } else {
                                            Toast.makeText(context, "TTS tizimi tayyorlanmoqda...", Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    modifier = Modifier
                                        .size(42.dp)
                                        .background(HabitGold, CircleShape)
                                ) {
                                    Icon(
                                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                        contentDescription = null,
                                        tint = HabitDarkBg,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column {
                                    Text(
                                        text = if (isPlaying) "🔊 Ovozli ijro etilmoqda..." else "⏸️ Pauzada",
                                        color = if (isPlaying) HabitGold else HabitInkSoft,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Spiker: ${sample.teacherName}",
                                        color = HabitInkSoft,
                                        fontSize = 10.5.sp
                                    )
                                }
                            }

                            // Speed Selectors
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                listOf(0.8f, 1.0f, 1.2f).forEach { spd ->
                                    val isCur = speechSpeed == spd
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = if (isCur) HabitGold else HabitCardBg,
                                        modifier = Modifier.clickable {
                                            speechSpeed = spd
                                            if (isPlaying && ttsEngine != null) {
                                                ttsEngine.setSpeechRate(spd)
                                                ttsEngine.speak(sample.hiddenSpokenContent, TextToSpeech.QUEUE_FLUSH, null, "read_listen_${sample.id}")
                                            }
                                        }
                                    ) {
                                        Text(
                                            text = "${spd}x",
                                            color = if (isCur) HabitDarkBg else HabitInk,
                                            fontSize = 10.5.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Section Label: Story Script Text
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "📖 Story Transcript & Reading:",
                        color = HabitGold,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(
                        onClick = {
                            val clipManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            clipManager.setPrimaryClip(ClipData.newPlainText("Story Text", sample.hiddenSpokenContent))
                            Toast.makeText(context, "Matn xotiraga nusxalandi!", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Nusxa olish", tint = HabitGold, modifier = Modifier.size(16.dp))
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Scrollable Story Reader Container
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = HabitDarkBg,
                    border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.2f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            Text(
                                text = sample.hiddenSpokenContent,
                                color = HabitInk,
                                fontSize = 14.sp,
                                lineHeight = 22.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = HabitCardBg,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(
                                        text = "🇺🇿 O'zbekcha mazmuni:",
                                        color = HabitGold,
                                        fontSize = 11.5.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = sample.descriptionUz,
                                        color = HabitInkSoft,
                                        fontSize = 12.sp,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Action Bar: Load into Arena or Open in Dictation
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            ttsEngine?.stop()
                            onPlayInArena()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = HabitGold),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.SportsEsports, contentDescription = null, tint = HabitDarkBg, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("🎮 Audio Arena", color = HabitDarkBg, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = {
                            ttsEngine?.stop()
                            onOpenInEditor()
                        },
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, HabitGold),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.EditNote, contentDescription = null, tint = HabitGold, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("📝 Diktant", color = HabitGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

/**
 * 📚 ScriptLibraryGridView:
 * The complete Grid View displaying the pre-loaded 50 story scripts.
 * Contains search bar, category filter chips carousel, and a 2-column responsive grid
 * of `ScriptLibraryGridCard` items with immediate 'Read/Listen' actions.
 */
@Composable
fun ScriptLibraryGridView(
    ttsEngine: TextToSpeech?,
    isTtsReady: Boolean,
    onLoadIntoScript: (title: String, fullText: String, sampleId: String) -> Unit,
    onLoadIntoArena: (title: String, sampleId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val allSamples = remember { ScriptSampleData.fiftySamples }
    val categories = remember {
        listOf("Barchasi") + allSamples.map { it.category }.distinct()
    }

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Barchasi") }
    var activeModalSample by remember { mutableStateOf<ScriptSampleAudio?>(null) }

    val filteredSamples = remember(searchQuery, selectedCategory) {
        allSamples.filter { sample ->
            val matchesCategory = selectedCategory == "Barchasi" || sample.category == selectedCategory
            val matchesSearch = searchQuery.isBlank() ||
                    sample.title.contains(searchQuery, ignoreCase = true) ||
                    sample.descriptionUz.contains(searchQuery, ignoreCase = true) ||
                    sample.teacherName.contains(searchQuery, ignoreCase = true) ||
                    sample.hiddenSpokenContent.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HabitDarkBg)
    ) {
        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Hikoya nomi, mavzu yoki so'z qidirish...", color = HabitInkSoft, fontSize = 12.sp) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null, tint = HabitGold, modifier = Modifier.size(18.dp))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 4.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = HabitCardBg,
                unfocusedContainerColor = HabitCardBg,
                focusedBorderColor = HabitGold,
                unfocusedBorderColor = HabitGold.copy(alpha = 0.25f),
                focusedTextColor = HabitInk,
                unfocusedTextColor = HabitInk
            ),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        // Categories filter horizontal carousel
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(categories) { cat ->
                val isSelected = selectedCategory == cat
                val icon = if (cat == "Barchasi") "📚" else getScriptCategoryIcon(cat)
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedCategory = cat },
                    label = {
                        Text(
                            text = "$icon $cat",
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = HabitGold,
                        selectedLabelColor = HabitDarkBg,
                        containerColor = HabitCardBg,
                        labelColor = HabitInk
                    ),
                    border = BorderStroke(1.dp, if (isSelected) HabitGold else HabitGold.copy(alpha = 0.25f))
                )
            }
        }

        // Stats summary row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "📚 Jami ${filteredSamples.size} ta audio hikoya skripti",
                color = HabitInkSoft,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "⚡ 'Read/Listen' bilan darhol ochiladi",
                color = HabitGold,
                fontSize = 10.5.sp
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // 2-Column Responsive Grid of ScriptLibraryGridCard items
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 2.dp),
            contentPadding = PaddingValues(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredSamples, key = { it.id }) { sample ->
                ScriptLibraryGridCard(
                    sample = sample,
                    onReadListenClick = {
                        activeModalSample = sample
                    },
                    onPlayInArena = {
                        onLoadIntoArena(sample.title, sample.id)
                    },
                    onOpenInEditor = {
                        onLoadIntoScript(sample.title, sample.hiddenSpokenContent, sample.id)
                    }
                )
            }
        }
    }

    // Modal Sheet / Dialog for immediate Read & Listen
    activeModalSample?.let { sample ->
        ScriptReadListenDialog(
            sample = sample,
            ttsEngine = ttsEngine,
            isTtsReady = isTtsReady,
            onDismiss = { activeModalSample = null },
            onPlayInArena = {
                val s = activeModalSample
                activeModalSample = null
                if (s != null) onLoadIntoArena(s.title, s.id)
            },
            onOpenInEditor = {
                val s = activeModalSample
                activeModalSample = null
                if (s != null) onLoadIntoScript(s.title, s.hiddenSpokenContent, s.id)
            }
        )
    }
}
