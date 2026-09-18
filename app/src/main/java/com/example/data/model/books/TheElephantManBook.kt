package com.example.data.model.books

import com.example.data.model.BookChapter
import com.example.data.model.GradedBook

object TheElephantManBook {

    val CHAPTERS = listOf(
        BookChapter(
            chapterNumber = 1,
            title = "1-Bob: The Dark Shop in Whitechapel",
            contentParagraphs = listOf(
                "My name is Dr. Frederick Treves. In the damp November of 1884, I was a young, ambitious lecturer in anatomy and consultant surgeon at the London Hospital in Whitechapel. The hospital stood in one of the poorest, most overcrowded districts of East London, where dense yellow fog from the Thames wrapped around chimney pots and soot covered every windowsill.",
                "Opposite our hospital gate, across the muddy cobblestone roadway, stood a row of derelict, ramshackle shops. One dreary afternoon, as cold drizzling rain turned the gutters into black streams, I noticed a crude hand-painted canvas banner hanging above a greasy shop window.",
                "The banner depicted a monstrous, frightening hybrid creature: half man, half elephant. Beneath this lurid illustration, bold black letters announced: 'COME AND SEE THE ELEPHANT MAN. ADMISSION TWO PENCE. THE GREATEST WONDER OF NATURE.'",
                "I felt a sudden jolt of professional curiosity mixed with quiet sadness. Was this an elaborate theatrical hoax, or was there genuinely an unfortunate human soul suffering from a rare, undocumented physical pathology? I pulled my heavy wool overcoat tight against the biting wind and crossed the road.",
                "The doorway was guarded by a dirty curtain made of rough sackcloth. A burly, sour-smelling man with greased black hair and yellow teeth sat on a wooden crate, smoking a clay pipe. This was Simon Silcock, the showman.",
                "'Two pence to see the monster, guv'nor,' Silcock grunted, rubbing his filthy knuckles together. 'He is hideous enough to curdle fresh milk, but he won't bite if I am holding the stick.'",
                "I placed two silver coins into his outstretched palm. 'I am a surgeon from the hospital opposite. I wish to see him in private for five minutes without an audience.'",
                "Silcock pocketed the coins with a crooked grin and pulled back the grease-stained curtain. I stepped inside the freezing, dim room, unaware that this single moment would alter the trajectory of my entire life and career forever."
            ),
            targetVocabulary = listOf("anatomy", "derelict", "ramshackle", "dreary", "hybrid", "pathology", "burly", "trajectory")
        ),
        BookChapter(
            chapterNumber = 2,
            title = "2-Bob: The Creature in the Corner",
            contentParagraphs = listOf(
                "The room was small, pitch-black, and smelled foully of damp straw, cold cabbage, and stale unwashed wool. In the corner, huddled beneath an old horse blanket beside a rusted brazier, sat a silent, motionless shape.",
                "'Get up, Merrick! Show the gentleman your head!' Silcock barked, striking the wooden floor with his iron-tipped ash stick.",
                "The blanket fell away, and a figure slowly rose into the pale shaft of daylight filtering through a dirty skylight. I gasped and stepped back, involuntarily gripping the lapels of my coat. In all my years in medical wards and dissection laboratories, I had never witnessed such extraordinary bodily distortion.",
                "His head was enormous, misshapen like a gigantic lumpy loaf of bread, twice the size of any normal man's skull. Heavy folds of grey, cauliflower-like flesh hung down over his forehead, almost concealing his right eye.",
                "His upper jaw projected forward like an animal's muzzle, and from his nose sprouted a thick protrusion of spongy skin resembling a miniature trunk. His right arm and shoulder were gigantic, heavy, and distorted by hard bony tumors, with a useless hand like the fin of a tortoise.",
                "Yet in striking, poignant contrast, his left arm was delicate, shapely, and possessed of skin as soft and unblemished as that of a young girl. His spine was bent into a painful curve, forcing him to walk with a heavy, dragging limp.",
                "What struck me most profoundly, however, was not his disfigurement, but his eyes. Out from beneath the terrible mass of overgrown skin, his dark brown left eye met mine. It did not hold fury or madness, but an ocean of quiet, heartbreaking gentleness and sorrow.",
                "I realized with a surge of remorse that this was not a beast or a circus prop, but a living, feeling human being enduring unspeakable loneliness. 'What is your name, my friend?' I asked quietly. A soft, breathless whisper emerged from his mouth: 'Joseph... Joseph Merrick.'"
            ),
            targetVocabulary = listOf("brazier", "dissection", "pathology", "poignant", "unblemished", "remorse", "profoundly", "disfigurement")
        ),
        BookChapter(
            chapterNumber = 3,
            title = "3-Bob: The Secret Examination",
            contentParagraphs = listOf(
                "I could not leave this man in that squalid freezing cellar. 'Mr. Silcock,' I said firmly, 'I want to bring Mr. Merrick across to the hospital lecture theater tomorrow morning for an official scientific examination. I will pay you twelve shillings for two hours.'",
                "Silcock's eyes glittered with greed. 'Twelve shillings? Done, doctor! But you must bring him back before the evening crowds arrive at six.'",
                "The following morning was bitterly cold. A closed hansom cab waited outside the shop. To protect Joseph from the cruel mockery of street urchins and onlookers, Silcock had dressed him in an enormous grey cloak and an oversized black cap with a linen curtain that covered his face, leaving only two small eye slits.",
                "Walking beside him, I could hear how difficult each step was. His breathing was heavy and labored due to chronic bronchial compression. When we reached the hospital, I helped him up the back service stairs to avoid the public corridors.",
                "Inside my warm, firelit consulting room, I removed his heavy veil and examined him thoroughly with a stethoscope, magnifying lens, and measuring calipers. Despite his terrifying appearance, Joseph never uttered a single word of complaint or anger.",
                "When I gently touched his deformed wrist, he flinched, not in pain, but in surprise. Later I realized the tragic truth: until that morning, nobody had ever touched him with kindness or professional respect; every hand had held a stick, a stone, or a whip.",
                "When the examination ended, I gave him my professional card with my name and London Hospital address written in embossed black ink. 'Keep this in your coat pocket, Joseph,' I said gently. 'If you are ever in desperate trouble, show this card to anyone, and they will find me.'",
                "He held the small white cardboard rectangle with his delicate left hand as if it were a precious diamond. A single tear tracked down the uneven surface of his cheek as he whispered, 'Thank you, kind sir.'"
            ),
            targetVocabulary = listOf("squalid", "urchins", "bronchial", "calipers", "flinched", "embossed", "rectangle", "consulting")
        ),
        BookChapter(
            chapterNumber = 4,
            title = "4-Bob: Abandoned in Brussels",
            contentParagraphs = listOf(
                "A few weeks after our meeting, the British police banned all public freak-show exhibitions in London, declaring them inhumane and degrading. Silcock, furious at losing his income, packed Joseph onto a steamship and fled across the English Channel to Belgium.",
                "For two grueling years, Joseph endured unimaginable hardship in Brussels, Ghent, and Antwerp. Belgian authorities were even stricter, shutting down exhibitions almost immediately. Silcock grew desperate, drunken, and increasingly abusive.",
                "One freezing night in December 1886, in a cheap boarding house near Brussels railway station, Silcock committed his final act of cruelty. While Joseph was asleep, Silcock stole all of Joseph's life savings—fifty pounds carefully hidden in a tin box—and boarded a train to Paris, abandoning Joseph penniless without food or lodging.",
                "Joseph was cast onto the icy cobblestones of Brussels, speaking no French, unable to earn a crust of bread. The police put him on an overnight passenger ferry back to England, eager to rid their city of a sight so distressing to travelers.",
                "The ferry arrived at Dover at four o'clock on a foggy Tuesday morning. Joseph dragged his crippled body onto the passenger train to London's Liverpool Street Station. He had eaten nothing for two days; his shoes had worn through to the skin, and he was burning with a feverish cough.",
                "When Joseph stepped onto the crowded platform at Liverpool Street Station, pandemonium erupted. Commuters screamed, women fainted, and a violent mob of curious cab drivers and porters surrounded him, mocking him and trying to tear off his mask.",
                "Joseph fell to the dirty stone floor, curling into a ball beneath his wet cloak, weeping in terror. Two railway policemen pushed through the shouting mob, grabbed Joseph by the arms, and dragged him into a third-class waiting room to prevent a riot.",
                "Joseph sat shivering in the corner, clutching his chest. He could not speak English clearly through his exhaustion, but his trembling left hand reached inside his soaked coat pocket. He pulled out a creased, water-stained piece of white cardboard and held it up to the station inspector. It was my card."
            ),
            targetVocabulary = listOf("degrading", "grueling", "penniless", "ferry", "pandemonium", "commuters", "porters", "creased")
        ),
        BookChapter(
            chapterNumber = 5,
            title = "5-Bob: A Home in the London Hospital",
            contentParagraphs = listOf(
                "When the telegraph messenger arrived at the hospital, I immediately summoned my carriage and raced across the city to Liverpool Street Station. Pushing through the onlookers still gathered outside the waiting room, I found Joseph sitting on a wooden bench, trembling violently.",
                "As soon as his dark brown eye recognized me through the eye slits of his veil, he reached out his gentle left hand and began to weep with profound relief. 'Doctor... you came,' he whispered faintly.",
                "I brought him directly to the hospital. Our chief medical administrator, Mr. Carr Gomm, was a practical and compassionate gentleman. Together, we found two quiet ground-floor rooms in the Bedstead Square wing of the hospital, opening onto a walled garden shaded by green plane trees.",
                "The rooms were painted a soothing cream color, furnished with a wide feather bed, a comfortable armchair, a mahogany writing desk, and a warm coal-burning fireplace. For the first time in his thirty-four years of life, Joseph Merrick had a private sanctuary where no one could stare at him, mock him, or hurt him.",
                "Our nursing staff bathed him gently with warm lavender water, dressed his ulcerated skin with soothing carbolic ointments, and provided him with clean flannel pajamas. When hot beef soup and fresh buttered bread were placed before him, Joseph could scarcely believe his eyes.",
                "'Is this heaven, Dr. Treves?' he asked, looking around the peaceful room with wide, innocent wonder. 'Or will someone blow a whistle and tell me to stand up on a wooden box?'",
                "'No, Joseph,' I answered with a lump in my throat, placing a comforting hand on his shoulder. 'The circus is over. This is your home now, for as long as you live.'"
            ),
            targetVocabulary = listOf("compassionate", "administrator", "sanctuary", "lavender", "carbolic", "ulcerated", "scarcely", "innocent")
        ),
        BookChapter(
            chapterNumber = 6,
            title = "6-Bob: The Gentle Soul Awakens",
            contentParagraphs = listOf(
                "During the first month, Joseph remained painfully timid. Whenever a nurse entered the room with fresh towels or medicine, he would pull his blanket over his head, expecting harsh blows or derisive laughter.",
                "Gradually, under our patient care, his terror melted away like spring snow. I visited him every morning before my surgical rounds. We sat by the warm fireplace and spoke of books, history, and poetry. To my immense delight, I discovered that beneath his tragic exterior lived an intellect of exquisite sensitivity and imagination.",
                "Joseph was an avid reader. He treasured the plays of William Shakespeare, the poetry of Lord Tennyson, and romantic English novels. His voice, once choked with fear, became melodic, expressive, and thoughtful.",
                "One afternoon, I brought him a large box of colored cardboard, fine scissors, and pots of gum arabic. His delicate left hand possessed astonishing manual dexterity. Over the following three weeks, he painstakingly constructed an intricate, mathematically accurate miniature model of St. Philip's Cathedral.",
                "The model featured delicate flying buttresses, tall spires, tiny Gothic windows, and individual roof tiles cut from parchment. It was a masterpiece of architectural patience and grace.",
                "'Why do you love building cathedrals, Joseph?' I asked as he glued the final spire onto the roof with quiet devotion.",
                "'Because a cathedral is built to welcome everyone, Dr. Treves,' he replied softly, his voice full of reverent emotion. 'The rich, the poor, the beautiful, and the broken all bow their heads together under its arches, and God sees only their souls.'"
            ),
            targetVocabulary = listOf("timid", "derisive", "exquisite", "melodic", "dexterity", "buttresses", "parchment", "reverent")
        ),
        BookChapter(
            chapterNumber = 7,
            title = "7-Bob: Letters to the Times",
            contentParagraphs = listOf(
                "Keeping Joseph at the London Hospital permanently presented a serious administrative obstacle: hospital rules strictly forbade housing incurable patients who did not require active surgical intervention.",
                "Mr. Carr Gomm, our determined hospital chairman, decided to take a bold step. On December 4, 1886, he wrote a passionate, eloquent open letter to the editor of The Times, Britain's most influential national newspaper.",
                "In his letter, Mr. Gomm recounted Joseph Merrick's harrowing journey: his abandoned youth, the cruelty of freak shows, his robbery in Belgium, and his present condition. He concluded with a poignant appeal: 'Mr. Merrick is quiet, gentle, and intelligent. He asks only for a roof over his head and a little bread where he can finish his days in peace.'",
                "The public response was overwhelming. Within forty-eight hours, London's morning post delivered hundreds of letters to the hospital gates. Wealthy aristocrats, humble dockworkers, school teachers, and shopkeepers sent donations ranging from five-pound notes to shiny copper pennies.",
                "By the end of the week, over twenty-five hundred pounds had been collected—a fortune sufficient to maintain Joseph's rooms, food, and medical nursing care for the rest of his natural life.",
                "Along with money came gifts: cases of sweet oranges, jars of strawberry preserves, leather-bound classics, embroidered blankets, and warm woolen shawls sent by kind-hearted grandmothers from across the country.",
                "When I showed Joseph the towering stacks of letters and read him the heartfelt blessings written by ordinary Englishmen and women, he covered his face with his hands and wept for nearly an hour.",
                "'All my life, I believed everyone hated me because of my face,' he sobbed with joyful tears. 'Now I know that the world is filled with good, kind people.'"
            ),
            targetVocabulary = listOf("administrative", "incurable", "intervention", "eloquent", "harrowing", "aristocrats", "sufficient", "heartfelt")
        ),
        BookChapter(
            chapterNumber = 8,
            title = "8-Bob: A Royal Visit",
            contentParagraphs = listOf(
                "The story of the Elephant Man soon captivated London high society. Aristocrats, famous actors, and writers began requesting private audiences to meet the extraordinary gentleman of Bedstead Square.",
                "One bright May morning in 1887, our most illustrious visitor arrived unannounced: Alexandra, the Princess of Wales, accompanied by her royal lady-in-waiting.",
                "I was terrified that the sight of Joseph might frighten Her Royal Highness. But Princess Alexandra possessed a radiant spirit of sincere human empathy. As she stepped into Joseph's modest sitting room, she did not flinch, hesitate, or look away.",
                "She walked directly across the Persian rug, extended her gloved hand with a warm, enchanting smile, and said clearly: 'Good morning, Mr. Merrick. It is a genuine pleasure to make your acquaintance.'",
                "Joseph bowed as low as his curved spine permitted, kissing the royal fingers with chivalrous devotion. They sat by the open French doors overlooking the blooming lilac trees and chatted for nearly an hour.",
                "They spoke of music, literature, and the Princess's family. Alexandra admired his cardboard cathedral and his collection of pressed wildflowers. Before leaving, she presented him with an autographed royal portrait in a silver frame and an engraved pocket watch.",
                "Every Christmas thereafter, the Princess sent Joseph a handwritten letter and a basket of game and fresh fruit from the royal estate at Sandringham.",
                "Joseph placed her photograph proudly in the center of his mantelpiece. 'She shook my hand without fear,' Joseph told me every time I visited. 'She treated me not as a curiosity, but as a gentleman and an equal.'"
            ),
            targetVocabulary = listOf("illustrious", "empathy", "acquaintance", "chivalrous", "lilac", "autographed", "engraved", "mantelpiece")
        ),
        BookChapter(
            chapterNumber = 9,
            title = "9-Bob: Summer in the Countryside",
            contentParagraphs = listOf(
                "Despite his happiness at the hospital, Joseph harbored one deep, lifelong dream: he longed to walk in the green English countryside, far away from the soot, iron bridges, and suffocating brick walls of London.",
                "In July 1889, Lady Knightley of Fawsley offered us the use of a secluded gamekeeper's cottage on her vast Northamptonshire country estate. I accompanied Joseph on a private evening train out of St. Pancras Station.",
                "For six glorious weeks, Joseph lived in a paradise he had only ever read about in poetry books. The small thatched cottage stood on the edge of an ancient oak forest, surrounded by rolling clover meadows, singing brooks, and wild blackberry thickets.",
                "Joseph woke every morning at dawn to hear the dawn chorus of nightingales and skylarks. He spent hours sitting quietly on a mossy fallen log, tossing breadcrumbs to wild thrushes that learned not to fear him.",
                "Wild trout jumped in the crystal-clear stream; deer grazed peacefully in the evening mist just thirty yards from his cottage door. He walked through golden fields of wheat, breathing deep lungfuls of sweet, pollen-scented summer air.",
                "Local farm children, initially curious, soon realized how gentle the cloaked visitor was. They brought him baskets of wild raspberries and bouquets of blue cornflowers, which Joseph placed carefully in earthen jugs on his windowsill.",
                "In his letters to me in London, he wrote: 'Dear Dr. Treves, my heart is as light as a bird's feather. The sun does not ask what face I wear; it warms me just the same. I am happier than the wealthiest king on earth.'",
                "When he returned to Whitechapel in late August, his pale skin was tanned with health, his spirits soared with joy, and his room was adorned with dried meadow grasses and pine cones."
            ),
            targetVocabulary = listOf("harbored", "secluded", "gamekeeper", "thatched", "nightingales", "thrushes", "bouquets", "adorned")
        ),
        BookChapter(
            chapterNumber = 10,
            title = "10-Bob: The Final Slumber",
            contentParagraphs = listOf(
                "Because of the crushing weight of his enormous skull, Joseph could never sleep lying flat like a normal person; if he reclined on his back, the heavy bones would compress his windpipe and suffocate him. For years, he slept sitting upright in his armchair with his head resting on his bent knees.",
                "Yet his dearest secret wish was to lie down in a bed and sleep like other men. On the morning of April 11, 1890, our nursing sister entered his room with his breakfast tray. She found Joseph lying peaceful and motionless on his back upon his feather mattress.",
                "I rushed to his room immediately, but his gentle soul had already slipped away. During the night, he had attempted to sleep lying down, seeking that one experience of ordinary human slumber. His neck had dislocated under the sheer weight of his head, ending his life instantly and painlessly.",
                "On his bedside table, beside his Bible and his unfinished model of a sailing ship, lay a piece of paper containing four lines of poetry he had copied by hand from Isaac Watts:",
                "'Tis true my form is something odd,\nBut blaming me is blaming God;\nCould I create myself anew,\nI would not fail in pleasing you.'",
                "Beneath the verse, he had written his own conclusion: 'If I could pass from pole to pole, or grasp the ocean in a span, I must be measured by my soul; the mind's the standard of the man.'",
                "Joseph Merrick was buried with profound dignity and honors. He taught me, and all of Victorian society, that human beauty does not reside in physical symmetry or superficial outward perfection.",
                "His true beauty shone in his boundless patience, his gentle forgiveness of cruelty, and his pure, unbroken capacity to love. As long as humanity remembers courage and kindness, the gentle spirit of Joseph Merrick will never be forgotten."
            ),
            targetVocabulary = listOf("reclined", "suffocate", "upright", "dislocated", "verse", "symmetry", "superficial", "boundless")
        )
    )

    val BOOK = GradedBook(
        id = "elephant_man",
        title = "The Elephant Man",
        author = "Tim Vicary",
        level = "Stage 1 (400 so'z)",
        headwords = 400,
        iconEmoji = "🐘",
        synopsis = "Doktor Frederik Treves London xarobasidan Jozef Merrikni topadi. 10 ta boy bobda uning mashaqqatli o'tmishi, shifoxonadagi panohi, malika tashrifi, yozgi qishloq mo''jizasi va insoniy qadr-qimmatning mangu g'alabasi hikoya qilinadi.",
        chapters = CHAPTERS
    )
}
