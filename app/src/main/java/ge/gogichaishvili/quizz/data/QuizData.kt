package ge.gogichaishvili.quizz.data

object QuizData {

    fun getQuizData(): MutableList<Question> {

        val data = mutableListOf<Question>()

        data.add(
            Question(
                1,
                "რომელი მეთოდი ანაცვლებს ფრაგმენტებს?",
                mutableListOf(
                    Answer(1, "Replace", hasAlreadyAnswered = false, shouldUpdateUi = false),
                    Answer(2, "Commit", hasAlreadyAnswered = false, shouldUpdateUi = false),
                    Answer(3, "Add", hasAlreadyAnswered = false, shouldUpdateUi = false),
                    Answer(4, "addToBackStack", hasAlreadyAnswered = false, shouldUpdateUi = false)
                ),
                1
            )
        )

        data.add(
            Question(
                2,
                "როგორ უნდა მივწვდეთ fragmentManager-ს ფრაგმენტში?",
                mutableListOf(
                    Answer(
                        1,
                        "fragmentManager",
                        hasAlreadyAnswered = false,
                        shouldUpdateUi = false
                    ),
                    Answer(
                        2, "parentFragmentManager",
                        hasAlreadyAnswered = false,
                        shouldUpdateUi = false
                    ),
                    Answer(
                        3, "supportFragmentManager",
                        hasAlreadyAnswered = false,
                        shouldUpdateUi = false
                    ),
                    Answer(
                        4, "requireFragmentManager",
                        hasAlreadyAnswered = false,
                        shouldUpdateUi = false
                    )
                ),
                2
            )
        )

        data.add(
            Question(
                3,
                "რომელი მეთოდი იძახება ფრაგმენტში ყველაზე ადრე?",
                mutableListOf(
                    Answer(1, "onResume", hasAlreadyAnswered = false, shouldUpdateUi = false),
                    Answer(2, "onPause", hasAlreadyAnswered = false, shouldUpdateUi = false),
                    Answer(3, "onCreate", hasAlreadyAnswered = false, shouldUpdateUi = false),
                    Answer(4, "onCreateView", hasAlreadyAnswered = false, shouldUpdateUi = false)
                ),
                3
            )
        )

        data.add(
            Question(
                4,
                "რომელი მეთოდში უნდა გავანულოთ binding ცვლადი?",
                mutableListOf(
                    Answer(1, "onDestroy", hasAlreadyAnswered = false, shouldUpdateUi = false),
                    Answer(2, "onDestroyView", hasAlreadyAnswered = false, shouldUpdateUi = false),
                    Answer(3, "onPause", hasAlreadyAnswered = false, shouldUpdateUi = false),
                    Answer(4, "onStop", hasAlreadyAnswered = false, shouldUpdateUi = false)
                ),
                2
            )
        )

        data.add(
            Question(
                5,
                "რომელ მეთოდში უნდა დავწეროთ ძირითადი კოდი ფრაგმენტში?",
                mutableListOf(
                    Answer(1, "onPause", hasAlreadyAnswered = false, shouldUpdateUi = false),
                    Answer(2, "onCreate", hasAlreadyAnswered = false, shouldUpdateUi = false),
                    Answer(3, "onCreateView", hasAlreadyAnswered = false, shouldUpdateUi = false),
                    Answer(4, "onViewCreated", hasAlreadyAnswered = false, shouldUpdateUi = false)
                ),
                3
            )
        )

        data.add(
            Question(
                6,
                "სად უნდა გამოვაცხადოთ სტატიკური მეთოდები კოტლინში?",
                mutableListOf(
                    Answer(1, "init ბლოკში", hasAlreadyAnswered = false, shouldUpdateUi = false),
                    Answer(2, "კონსტრუქტორში", hasAlreadyAnswered = false, shouldUpdateUi = false),
                    Answer(
                        3, "Companion object-ში",
                        hasAlreadyAnswered = false,
                        shouldUpdateUi = false
                    ),
                    Answer(4, "კლასის ტანში", hasAlreadyAnswered = false, shouldUpdateUi = false)
                ),
                3
            )
        )

        data.add(
            Question(
                7,
                "რომელი ტიპი უნდა გადავაწოდოთ ფრაგმენტს არგუმენტად?",
                mutableListOf(
                    Answer(1, "Bundle", hasAlreadyAnswered = false, shouldUpdateUi = false),
                    Answer(2, "Pair", hasAlreadyAnswered = false, shouldUpdateUi = false),
                    Answer(3, "String", hasAlreadyAnswered = false, shouldUpdateUi = false),
                    Answer(4, "Char", hasAlreadyAnswered = false, shouldUpdateUi = false)
                ),
                1
            )
        )

        return data
    }
}