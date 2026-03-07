package com.ruse.world.content;

import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;

import java.util.Random;

public class TriviaSystem {

	enum TriviaData {
		QUESTION_1("When was Athens Founded?", "2025"),
		QUESTION_2("Who is the owner of Athens?", "Hades"),
		QUESTION_3("What is the second Multi-boss in Medium tier?", "Coeus"),
		QUESTION_4("First to type: Athens is number 1", "Athens is number 1"),
		QUESTION_5("How do you view the store on Athens?", "::store"),
		QUESTION_6("How do you seek Staff assistance on Athens?", "::help"),
		QUESTION_7("The Lion, The Witch, & _ _ ?", "The Wardrobe"),
		QUESTION_8("Quick math: 19 + 25?", "44"),
		QUESTION_9("Unscramble: thensa pace", "Athens cape"),
		QUESTION_10("How many Ancient bones do you need to make a offering?", "50000"),
		QUESTION_11("Which US state has the longest cave system in the world?", "Kentucky"),
		QUESTION_12("Unscramble: esttoohont","Stonetooth"),
		QUESTION_13 ("What can be broken but is never held?", "a promise"),
		QUESTION_14("How many pets does it take to make a Mini me?", "6"),
		QUESTION_15("How many NPCS are in the Starter Zone?", "101"),
		QUESTION_16("What is the cost to upgrade an exotic mask to Athens mask?", "500k"),
		QUESTION_17("How many Nemesis kills do you need for ascension?", "5000"),
		QUESTION_18("Who was the first woman pilot to fly solo across the Atlantic?", "Amelia Earhart"),
		QUESTION_19("How many votes does it take to spawn the vote boss?", "75"),
		QUESTION_20("What is the last Multi-boss in easy tier?", "Monosaur"),
		QUESTION_21("What global boss requires 75k KC?", "galaxy"),
		QUESTION_22("Hunting unicorns is legal in what US state?", "Michigan"),
		QUESTION_23("Unscramble: abgoll kento", "Global Token"),
		QUESTION_24("How many staircases does Hogwarts have?", "142"),
		QUESTION_25("How many elements are on the periodic table?", "118"),
		QUESTION_26("What does DNA stand for?", "Deoxyribonucleic acid"),
		QUESTION_27("How many Charon kills do you need to get to master tier?", "3500"),
		QUESTION_28("In what type of matter are atoms most tightly packed?", "Solids"),
		QUESTION_29("Unscramble: snismee", "Nemesis"),
		QUESTION_30("Unscramble: lygxaa dorvreol", "Galaxy Overlord"),
		QUESTION_31("What is the highest donation rank amount?", "35000"),
		QUESTION_32("What is the deadliest insect?", "Mosquito"),
		QUESTION_33("Which country was the Caesar salad invented in?", "Mexico"),
		QUESTION_34("Unscramble: het rblhegnirtgi", "the lightbringer"),
		QUESTION_35("First to type: abcdefghijklmnopqrstuvwxyz", "abcdefghijklmnopqrstuvwxyz"),
		QUESTION_36("How many Npcs are in the arcade?", "26"),
		QUESTION_37("When was the Eiffel Tower completed?", "1889"),
		QUESTION_38("Who painted the Mona Lisa?", "Leonardo da Vinci"),
		QUESTION_39("What is the capital city of Australia?", "Canberra"),
		QUESTION_40("Which planet is known as the Red Planet?", "Mars"),
		QUESTION_41("Who wrote the play Romeo and Juliet?", "William Shakespeare"),
		QUESTION_42("In which country did the Olympic Games originate?", "Greece"),
		QUESTION_43("What is the chemical symbol for the element gold?", "Au"),
		QUESTION_44("What is the largest organ in the human body?", "Skin"),
		QUESTION_45("Who is the author of the Harry Potter book series?", "J.K. Rowling"),
		QUESTION_46("Which country is known for inventing the game of golf?", "Scotland"),
		QUESTION_47("Who painted the Sistine Chapel ceiling?", "Michelangelo"),
		QUESTION_48("What is the tallest mountain in the world?", "Mount Everest"),
		QUESTION_49("What is the largest ocean on Earth?", "Pacific Ocean"),
		QUESTION_50("Who discovered penicillin?", "Alexander Fleming"),
		QUESTION_51("What is the national animal of China?", "Giant Panda"),
		QUESTION_52("What is the chemical symbol for the element iron?", "Fe"),
		QUESTION_53("Which planet is known as the 'Morning Star' or 'Evening Star'?", "Venus"),
		QUESTION_54("Who is the Greek god of the sea?", "Poseidon"),
		QUESTION_55("What is the currency of Japan?", "Japanese Yen"),
		QUESTION_56("Who wrote the novel To Kill a Mockingbird?", "Harper Lee"),
		QUESTION_57("Which is the largest continent by land area?", "Asia"),
		QUESTION_58("What is the capital city of Canada?", "Ottawa"),
		QUESTION_59("Who painted the Starry Night?", "Vincent van Gogh"),
		QUESTION_60("In which city would you find the Colosseum?", "Rome"),
		QUESTION_61("What is the chemical symbol for the element oxygen?", "O"),
		QUESTION_62("Which country is famous for its tulips?", "Netherlands"),
		QUESTION_63("Who wrote the novel Pride and Prejudice?", "Jane Austen"),
		QUESTION_64("What is the largest organ inside the human body?", "Liver"),
		QUESTION_65("Which country is known as the Land of the Rising Sun?", "Japan"),
		QUESTION_66("Who was the first person to step on the moon?", "Neil Armstrong"),
		QUESTION_67("What is the capital city of Spain?", "Madrid"),
		QUESTION_68("Who painted the Last Supper?", "Leonardo da Vinci"),
		QUESTION_69("In which city would you find the Statue of Liberty?", "New York City"),
		QUESTION_70("What is the chemical symbol for the element sodium?", "Na"),
		QUESTION_71("Which country is known for its pyramids?", "Egypt"),
		QUESTION_72("Who is the author of the book 1984?", "George Orwell"),
		QUESTION_73("What is the largest desert in the world?", "Sahara Desert"),
		QUESTION_74("Which planet is known as the 'Blue Planet'?", "Earth"),
		QUESTION_75("Who wrote the novel Moby-Dick?", "Herman Melville"),
		QUESTION_76("Which country is known for its flamenco dance?", "Spain"),
		QUESTION_77("What is the chemical symbol for the element carbon?", "C"),
		QUESTION_78("Who painted the painting Guernica?", "Pablo Picasso"),
		QUESTION_79("In which city would you find the Great Wall of China?", "Beijing"),
		QUESTION_80("What is the national bird of the United States?", "Bald Eagle"),
		QUESTION_81("What is the chemical symbol for the element helium?", "He"),
		QUESTION_82("Which country is famous for its kangaroos?", "Australia"),
		QUESTION_83("Who is the author of the book The Great Gatsby?", "F. Scott Fitzgerald"),
		QUESTION_84("What is the largest lake in Africa?", "Lake Victoria"),
		QUESTION_85("Who painted the painting The Persistence of Memory?", "Salvador Dalí"),
		QUESTION_86("What is the capital city of France?", "Paris"),
		QUESTION_87("Who is the Norse god of thunder?", "Thor"),
		QUESTION_88("What is the chemical symbol for the element hydrogen?", "H"),
		QUESTION_89("Which country is known for its tulips?", "Netherlands"),
		QUESTION_90("Who wrote the novel War and Peace?", "Leo Tolstoy"),
		QUESTION_91("What is the largest organ in the human body?", "Skin"),
		QUESTION_92("Which country is known for inventing the game of cricket?", "England"),
		QUESTION_93("Who painted the painting The Starry Night?", "Vincent van Gogh"),
		QUESTION_94("What is the national animal of Canada?", "Beaver"),
		QUESTION_95("Who is credited with discovering gravity after an apple fell on his head?", "Isaac Newton"),
		QUESTION_96("Who is the author of The Lord of the Rings trilogy?", "J.R.R. Tolkien"),
		QUESTION_97("Which animal is the national symbol of Australia?", "Kangaroo"),
		QUESTION_98("What is the highest-grossing film of all time? ", "Avengers:Endgame"),
		QUESTION_99("Who painted the ceiling of the Sistine Chapel?", "Michelangelo"),
		LAST_QUESTION("Quick math: 112 - 80?", "32");

		TriviaData(String question, String answer) {
			this.question = question;
			this.answer = answer;
		}

		private String question, answer;

		public String getQuestion() {
			return question;
		}

		public String getAnswer() {
			return answer;
		}
	}

	private static int timer = 2200; // 20minutes
	private static boolean active = false;
	private static TriviaData currentQuestion = null;
	
	public static String getCurrentQuestion() {
		return currentQuestion == null ? "None" : currentQuestion.getQuestion().toUpperCase().substring(0, 1) + currentQuestion.getQuestion().toLowerCase().substring(1);
	}

	public static void tick() {

		if (!active) {
			if (timer < 1) {
				startTrivia();
				timer = 2200;
			} else {
				timer--;
			}
		}
	}

	private static final TriviaData[] TRIVIA_DATA = TriviaData.values();

	private static void startTrivia() {
		setAndAskQuestion();
	}

	public static void setAndAskQuestion() {
		active = true;
		currentQuestion = TRIVIA_DATA[new Random().nextInt(TRIVIA_DATA.length)];
		World.sendMessage("<img=22><col=0><shad=6C1894>[TRIVIA]<img=22> <col=AF70C3>" + currentQuestion.getQuestion() + "");
		World.getPlayers().forEach(PlayerPanel::refreshPanel);
	}
	
	public static void answer(Player player, String answer) {
		if(!active) {
			player.sendMessage("@red@There is no trivia going on at the moment");
			return;
		}
		if(answer.equalsIgnoreCase(currentQuestion.getAnswer())) {
			player.getInventory().add(6833, 1);
			player.getDailyTaskInterface().MiscTasksCompleted(8, 1);

			active = false;
			World.sendMessage("<img=22><col=0><shad=6C1894>[TRIVIA]<img=22> <col=AF70C3>" + player.getUsername() + "@bla@ has recieved a @blu@Goodiebag @bla@from Trivia");
			World.sendMessage("<img=22><col=0><shad=6C1894>[TRIVIA]<img=22> @bla@ The answer for the trivia to the question was @blu@" + currentQuestion.answer);
			currentQuestion = null;
			World.getPlayers().forEach(PlayerPanel::refreshPanel);//soz ok is there anything else u need or is that all
			player.sendMessage("@bla@congrats, You have guessed correctly and received a@blu@ Goodie bag@bla@!");
			
		} else {
			player.sendMessage("@bla@Incorrect answer - your answer was: @red@" + answer);
		}
	}

}
