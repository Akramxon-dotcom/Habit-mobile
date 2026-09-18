package com.example.data.model.books

import com.example.data.model.BookChapter
import com.example.data.model.GradedBook

object SherlockHolmesBook {

    val CHAPTERS = listOf(
        BookChapter(
            chapterNumber = 1,
            title = "1-Bob: The Visitor from the Priory School",
            contentParagraphs = listOf(
                "On a bitter, stormy Thursday morning in October, my friend Sherlock Holmes and I were sitting by a roaring fire in our comfortable sitting room at 221B Baker Street. Outside, cold rain lashed against the windows and London traffic splashed through pools of black mud.",
                "Suddenly, frantic footsteps pounded up our seventeen wooden stairs. Our door burst open without the courtesy of a knock. A tall, distinguished gentleman in a drenched black wool coat collapsed into our armchair, gasping for breath like a drowning swimmer.",
                "'My dear sir!' I exclaimed, jumping up to offer him a glass of brandy. His face was ashen, his silver hair disheveled, and his eyes wide with wild terror.",
                "'Mr. Holmes! You must come at once!' the man gasped, clutching his throat. 'I am Dr. Thorneycroft Huxtable, founder and headmaster of the Priory Preparatory School near Mackleton in Northern England. The school is ruined, and the boy is gone!'",
                "Holmes leaned back in his chair, joining his fingertips together in his characteristic pose of deep concentration. 'Pray compose yourself, Dr. Huxtable. Take a sip of brandy and tell us plainly who is missing.'",
                "'Lord Saltire! The ten-year-old only son and sole heir of the Duke of Holdernesse!' Dr. Huxtable cried. 'The Duke is one of the wealthiest cabinet ministers in the British Empire. If harm comes to the boy, the entire nation will be plunged into mourning!'",
                "Holmes's grey eyes flashed with keen intellectual excitement. A case involving the highest ranks of British nobility always signaled high stakes and deep deception. 'When did the young lord disappear, and under what circumstances?' Holmes demanded.",
                "'Three nights ago, on Tuesday midnight,' the headmaster whispered, trembling from head to foot. 'The boy vanished from his second-floor dormitory window. But that is not all: our German schoolmaster, Herr Heidegger, has also disappeared, along with his new pneumatic-tyre bicycle!'"
            ),
            targetVocabulary = listOf("frantic", "distinguished", "headmaster", "preparatory", "heir", "nobility", "deception", "dormitory")
        ),
        BookChapter(
            chapterNumber = 2,
            title = "2-Bob: The Duke's Secret Despair",
            contentParagraphs = listOf(
                "Dr. Huxtable reached into his leather briefcase and pulled out a crisp parchment telegram bearing the royal seal of Holdernesse Castle. He handed it to Holmes with shaking fingers.",
                "'The Duke has authorized an extraordinary reward of five thousand pounds for the boy's safe return,' Dr. Huxtable explained, 'and an additional one thousand pounds for the identity of the kidnapper. Scotland Yard detectives are baffled. You are our last hope, Mr. Holmes.'",
                "Holmes glanced at the massive reward figure with an amused smile. 'The Duke values his heir highly, Watson. But money will not buy time. Three days have already passed, which gives the criminal an enormous advantage. Pack your warm ulster coat and your service revolver, Watson. We catch the noon express train to Derbyshire.'",
                "During the three-hour railway journey north through misty moors and rolling limestone hills, Holmes sat in complete silence, reviewing maps of Derbyshire and newspaper clippings regarding the Duke's family history.",
                "'The Duke of Holdernesse is a proud, austere man, Watson,' Holmes remarked quietly as the train wheels clattered over the rails. 'His marriage was profoundly unhappy; his Duchess separated from him two years ago and now resides in the south of France.'",
                "'Could the mother have arranged the boy's abduction to gain custody?' I suggested, looking out at the bleak grey limestone peaks.",
                "'A reasonable hypothesis, Watson,' Holmes replied thoughtfully. 'Yet Lord Saltire loved his father dearly and wrote to his mother weekly. We must also examine the mysterious disappearance of the German master, Herr Heidegger, whose bicycle was taken from the school shed in the dead of night.'",
                "As our train pulled into Mackleton Station, a private carriage emblazoned with the ducal crest of Holdernesse was waiting on the cobblestone platform to carry us up onto the desolate, wind-swept moorlands."
            ),
            targetVocabulary = listOf("parchment", "authorized", "baffled", "ulster", "revolver", "austere", "abduction", "crest")
        ),
        BookChapter(
            chapterNumber = 3,
            title = "3-Bob: The Cold Trail on the Moor",
            contentParagraphs = listOf(
                "The Priory School stood in a sheltered valley on the edge of the vast, desolate Peak District moorlands. Behind the red-brick Tudor buildings stretched miles of peat bogs, heather-covered ridges, and jagged limestone ravines known as the Black Moor.",
                "Holmes began his investigation immediately, refusing even a cup of hot tea. On hands and knees with his pocket magnifying glass and a boxwood measuring rule, he examined the wet earth beneath Lord Saltire's bedroom window.",
                "'The boy climbed down this sturdy ivy vine,' Holmes announced, pointing to several torn tendrils and scraped mortar joints. 'His boots left clear impressions in the soft flowerbed below. Notice the light tread: he was not dragged or coerced; he came down willingly.'",
                "We followed the boy's footprints across the lawn to the boundary wall, where a wrought-iron side gate opened directly onto the wild moor. Just outside the gate, however, a chaotic scene of tracks met our eyes.",
                "'Look here, Watson!' Holmes called out, kneeling by a deep muddy depression in the sheep track. 'A bicycle passed here at high speed in the darkness! See how the narrow rubber tyres cut into the soft wet peat?'",
                "Holmes whipped out his magnifying glass and studied the pattern of the tread with intense fascination. 'This is a Dunlop tyre with a patchy patch on the front wheel and an embossed ribbed tread on the rear wheel. The rider was pedaling with furious haste toward the north.'",
                "Beside the bicycle tracks lay the heavy cloven footprints of a cow. But as Holmes leaned closer to the bovine prints, his eyebrows shot up in profound astonishment.",
                "'Watson, did you ever see a cow that galloped like a racehorse and took eight-foot strides between its prints?' Holmes muttered, his eyes dancing with excitement. 'There is deep villainy afoot on this moor!'"
            ),
            targetVocabulary = listOf("desolate", "tendrils", "mortar", "coerced", "depression", "peat", "bovine", "villainy")
        ),
        BookChapter(
            chapterNumber = 4,
            title = "4-Bob: The Tragic Bicycle",
            contentParagraphs = listOf(
                "We advanced north across the treacherous peat bog, leaping from one tuft of tough heather to another. The autumn wind whistled mournfully through the gorse bushes, and low grey clouds threatened another downpour.",
                "About two miles from the school, where the moor rose toward a desolate limestone ridge known as Ragged Shaw, Holmes suddenly froze like a hunting pointer. He raised his hand, signaling me to stop.",
                "Ahead of us, half-concealed beneath a thick clump of thorny brambles in a deep ditch, lay a twisted metallic object. I hurried forward and gasped: it was a green Palmer-tyre bicycle, its front wheel crushed and handlebars bent at a horrific angle.",
                "Beside the ruined bicycle, half-submerged in the black mud, lay the lifeless body of a tall, athletic man with blond hair and steel-rimmed spectacles. It was Herr Heidegger, the missing German schoolmaster.",
                "I knelt down and examined the poor fellow. His skull had been shattered from behind by a tremendous, crushing blow delivered with a heavy blunt instrument.",
                "'He died bravely, Watson,' Holmes whispered with grim reverence, taking off his deerstalker cap. 'Observe the evidence: Heidegger looked out his bedroom window, saw the boy being abducted, threw his coat over his nightshirt, grabbed his bicycle, and pursued the kidnappers across the dark moor.'",
                "'The villains must have heard him following them,' I added, looking at the cruel wound. 'They ambushed him in this hollow.'",
                "'Indeed,' Holmes agreed, inspecting the ground with grim intensity. 'And here are the same strange cloven cow hoofprints circling the body! But no cow ever struck a man down with an iron hammer. We are hunting a cunning and ruthless adversary.'"
            ),
            targetVocabulary = listOf("treacherous", "brambles", "submerged", "spectacles", "reverence", "pursued", "ambushed", "adversary")
        ),
        BookChapter(
            chapterNumber = 5,
            title = "5-Bob: The Secret of the Red Bull Inn",
            contentParagraphs = listOf(
                "Past Ragged Shaw, the bleak sheep trail merged into a rutted stone cart track leading toward the smoking chimneys of a lonely limestone hamlet. Standing beside the crossroads was a sinister, dilapidated stone tavern called The Fighting Cock.",
                "Outside the stable door stood a hulking, broad-shouldered man with a brutish face, greasy moleskin trousers, and fierce, bloodshot eyes. This was Reuben Hayes, the innkeeper and former head coachman to the Duke of Holdernesse.",
                "'Good day, landlord,' Holmes said cheerfully, stepping up to the porch. 'Can you provide my companion and me with a pair of riding horses to reach Chesterfield?'",
                "Hayes glared at us with undisguised hostility, spitting a mouthful of black tobacco juice into the dirt. 'I've no horses for strangers or snooping London swells,' he growled in a harsh northern dialect. 'Get off my land before I set my mastiff on you!'",
                "Holmes remained entirely unruffled by the man's insolence. But as Hayes turned back toward the stable, Holmes's keen eyes darted down to the damp mud near the stable door. Fresh horse hoofprints were clearly visible, leading directly inside the barn.",
                "'Look at those prints, Watson,' Holmes murmured under his breath as we feigned a retreat down the road. 'A horse was shod in that smithy very recently.'",
                "We climbed a low stone wall into a gorse thicket fifty yards away, out of sight of the tavern windows. Holmes pulled out his pocket telescope and trained it on the stables.",
                "'Watson, did you observe the innkeeper's boots?' Holmes whispered. 'The soles were caked with fresh black peat from Ragged Shaw ditch. Reuben Hayes was on that moor when poor Heidegger was struck down!'"
            ),
            targetVocabulary = listOf("dilapidated", "hulking", "moleskin", "hostility", "insolence", "feigned", "smithy", "thicket")
        ),
        BookChapter(
            chapterNumber = 6,
            title = "6-Bob: The Horseshoe with Cloven Hoofs",
            contentParagraphs = listOf(
                "We waited patiently in the gorse bushes until twilight draped the lonely valley in purple shadows. At seven o'clock, Reuben Hayes locked the front tavern door and entered his kitchen to drink ale with his stable hands.",
                "'Now, Watson! Move like a phantom,' Holmes whispered, leaping silently over the dry stone wall. We crept across the cobblestone yard and slipped into the dark, hay-scented interior of the blacksmith's forge behind the stables.",
                "The embers in the brick furnace were still glowing faint red. Holmes struck a vesta match and illuminated the anvil. In a wooden crate beneath the workbench lay several rusted iron horseshoes of peculiar design.",
                "Holmes picked one up with an exclamation of triumphant discovery. 'Elementary, my dear Watson! Look at this marvelous curiosity!'",
                "The shoe was made of heavy iron, but its underside was forged into the distinct double-lobed shape of a cow's hoof! When a horse wore these shoes, every step it took in the mud would appear to be the track of an ordinary cow!",
                "'An ancient moss-trooper trick from the Scottish border wars!' Holmes chuckled softly. 'The smugglers and cattle thieves of old used these false shoes to conceal their horses' tracks from the king's patrols. Reuben Hayes forged them right here on this anvil!'",
                "Suddenly, the creak of iron wagon wheels sounded on the road outside. Holmes extinguished the match instantly. Through a crack in the stable wall, we saw a closed brougham carriage pull up to the inn.",
                "The carriage door opened, and a tall, elegant young gentleman wearing a fur-collared overcoat stepped out. It was James Wilder, the Duke of Holdernesse's private secretary and trusted confidant!"
            ),
            targetVocabulary = listOf("draped", "vesta", "illuminated", "peculiar", "curiosity", "double-lobed", "brougham", "confidant")
        ),
        BookChapter(
            chapterNumber = 7,
            title = "7-Bob: The Conspiracy Unmasked",
            contentParagraphs = listOf(
                "James Wilder hurried into the tavern with a brass lantern. Holmes and I slipped around to the rear of the inn, where a lighted window illuminated the upstairs hayloft.",
                "Holmes hoisted me onto his shoulders, and I peered through the dirty pane. Inside a warm, carpeted room sat young Lord Saltire, wrapped in a woolen plaid blanket, sipping a cup of hot milk. He was unharmed, though his pale face showed signs of great distress.",
                "James Wilder entered the room, threw a heavy leather bag of gold sovereigns onto the table in front of Reuben Hayes, and spoke in heated, furious tones.",
                "'I told you only to hold the boy until the Duke signed the deed!' Wilder snarled. 'Why did you kill the German schoolmaster, you drunken fool? Now Scotland Yard will hang us both!'",
                "'He chased us on his bicycle, Wilder!' Hayes bellowed back, slamming his fist onto the table. 'He would have taken the boy back! I had to silence him with the crowbar! You promised me five thousand pounds and a safe passage to Australia!'",
                "Holmes tapped my ankle, signaling me to climb down. 'We have all the pieces of the puzzle, Watson,' he whispered as we sprinted through the darkness back toward the Priory School. 'The young lord is alive, but tomorrow morning will bring a dramatic reckoning.'"
            ),
            targetVocabulary = listOf("hayloft", "pane", "sovereigns", "deed", "snarled", "bellowed", "crowbar", "reckoning")
        ),
        BookChapter(
            chapterNumber = 8,
            title = "8-Bob: Confronting the Duke",
            contentParagraphs = listOf(
                "At nine o'clock the following morning, Holmes and I were ushered into the magnificent gothic library of Holdernesse Castle. High vaulted ceilings, stained glass windows, and rows of ancient leather volumes proclaimed centuries of feudal grandeur.",
                "Seated behind a massive carved oak desk was the Duke of Holdernesse himself. He was a commanding, aristocratic figure with piercing grey eyes, silver-streaked hair, and a face carved from stone. Beside him stood his secretary, James Wilder, looking pale and jittery as a caged bird.",
                "'Well, Mr. Holmes,' the Duke began with icy courtesy, 'have you brought any news of my son, or have you merely come to confess your inability to outwit common country thieves?'",
                "Holmes smiled serenely and took out his silver pocket watch. 'Your Grace, the six thousand pounds reward... Does it still hold?'",
                "The Duke frowned with aristocratic disdain. 'Certainly, sir. Every penny, provided my son is returned unharmed.'",
                "'Then pray write the cheque, Your Grace,' Holmes said calmly, 'for your son is at this moment in the hayloft of The Fighting Cock tavern, two miles from your gates.'",
                "The Duke turned pale as death, dropping his quill pen onto the blotter. James Wilder uttered a strangled gasp and leaned against the bookcase for support.",
                "'And what of the murderer of Herr Heidegger?' the Duke asked in a hollow whisper.",
                "'The murderer is Reuben Hayes, your former coachman,' Holmes answered sternly. 'And the man who masterminded the kidnapping sits right beside you—your illegitimate older son and secretary, James Wilder!'"
            ),
            targetVocabulary = listOf("vaulted", "feudal", "grandeur", "aristocratic", "jittery", "disdain", "blotter", "illegitimate")
        ),
        BookChapter(
            chapterNumber = 9,
            title = "9-Bob: The Confession of Jealousy",
            contentParagraphs = listOf(
                "The Duke slumped back into his tall leather chair, looking suddenly twenty years older. He covered his face with his trembling hands as James Wilder fell to his knees on the Persian rug, sobbing uncontrollably.",
                "'It is true, Mr. Holmes,' the Duke confessed in a broken voice. 'James is my firstborn son, born before my marriage. Because of the law, he could inherit neither my title nor my estates. He grew violently jealous of his younger half-brother, Lord Saltire.'",
                "'Wilder forged a letter in the Duchess's handwriting,' Holmes deduced with razor accuracy. 'He lured the boy down from his dormitory, promising to take him to see his mother in France. Wilder hired Reuben Hayes to conceal the boy at the inn, hoping to blackmail you into altering your will to leave him half your fortune.'",
                "'I swear on my mother's grave, I never intended any bloodshed!' James Wilder wept, clutching the Duke's knees. 'Heidegger's murder was entirely Reuben Hayes's doing!'",
                "'Where is Hayes now?' Holmes demanded sharply.",
                "'He fled on horseback at dawn toward Liverpool,' Wilder stammered. 'He planned to board a cargo ship for New York under a false name.'",
                "Holmes turned to me with military precision: 'Watson, run to the local telegraph office at Mackleton. Wire the Chief of Police at Liverpool Docks: arrest Reuben Hayes upon arrival. Give them his description: broad shoulders, moleskin trousers, brutish jaw, and a limp in his left leg.'"
            ),
            targetVocabulary = listOf("slumped", "uncontrollably", "lured", "bloodshed", "stammered", "precision", "telegraph", "brutish")
        ),
        BookChapter(
            chapterNumber = 10,
            title = "10-Bob: The Return of the Heir",
            contentParagraphs = listOf(
                "By noon, a squadron of Derbyshire county constables, accompanied by Holmes, Dr. Huxtable, and myself, surrounded The Fighting Cock tavern. We climbed the wooden ladder into the hayloft and rescued young Lord Saltire.",
                "When the carriage brought the boy through the great iron gates of Holdernesse Castle, the Duke abandoned all his cold aristocratic restraint. He sprinted down the stone steps, swept his young son into his arms, and wept openly with boundless gratitude.",
                "That evening, a telegraph message arrived from Liverpool: Reuben Hayes had been captured while stepping onto the gangplank of the transatlantic steamer SS American. The murderer was in chains, awaiting trial and the gallows.",
                "In the castle library, the Duke signed a bank draft for six thousand pounds and handed it to Sherlock Holmes with a deep, respectful bow. 'You have saved my family, Mr. Holmes. How can I ever repay your brilliant intellect?'",
                "Holmes slipped the draft into his breast pocket with a quiet smile. 'It was an intriguing problem, Your Grace. The cow-hoof horseshoes were an ingenious touch, but truth, like light, always pierces through the darkest fog.'",
                "As our evening train carried us back south toward the gas-lit streets of London, Holmes puffed thoughtfully on his briar pipe. 'A satisfactory conclusion, Watson,' he remarked as the Derbyshire moors faded into the starlit night.",
                "'A tragedy averted, a noble family reconciled, and justice served to the guilty. What more could an honest consulting detective ask from a day's work?'"
            ),
            targetVocabulary = listOf("squadron", "constables", "restraint", "gangplank", "transatlantic", "gallows", "ingenious", "reconciled")
        )
    )

    val BOOK = GradedBook(
        id = "sherlock_holmes",
        title = "Sherlock Holmes & Duke's Son",
        author = "Sir Arthur Conan Doyle",
        level = "Stage 1 (400 so'z)",
        headwords = 400,
        iconEmoji = "🕵️‍♂️",
        synopsis = "Gertsogning 10 yoshli o'g'li Priory maktabidan o'g'irlandi. 10 ta to'liq boyitilgan bob: 221B Beyker-strit, qorli botqoqlik, nemis o'qituvchisining jasorati, soxta sigir tuyoqli taqalar siri va gertsog saroyidagi hayajonli fosh etilish!",
        chapters = CHAPTERS
    )
}
