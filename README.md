# FilmFinder
## Инструкция по запуску
Перед запуском приложения необходимо указать токен кинопоиска в файле "gradle.properties".
В строку:
```sh
# write token here:
API_KEY = "token"
```
-


## Примеры запросов и ответов к серверу используемых в проекте
###  Поиск фильмов и сериалов
Запрос
```sh
https://api.kinopoisk.dev/v1.4/movie?page=1&limit=3&ageRating=18&year=2023&selectFields=id&selectFields=name&selectFields=alternativeName&selectFields=year&selectFields=ageRating&selectFields=countries&selectFields=poster&selectFields=rating
```
Ответ
```sh
{
    "docs": [
        {
            "id": 4647040,
            "alternativeName": null,
            "countries": [
                {
                    "name": "Россия"
                }
            ],
            "name": "Король и Шут",
            "poster": {
                "url": "https://image.openmoviedb.com/kinopoisk-images/6201401/1485ac9a-7796-470b-a3eb-85dc725d4ec0/orig",
                "previewUrl": "https://image.openmoviedb.com/kinopoisk-images/6201401/1485ac9a-7796-470b-a3eb-85dc725d4ec0/x1000"
            },
            "rating": {
                "kp": 8.17,
                "imdb": 7,
                "filmCritics": 0,
                "russianFilmCritics": 100,
                "await": null
            },
            "year": 2023,
            "ageRating": 18
        }
    ],
    "total": 835,
    "limit": 1,
    "page": 1,
    "pages": 835
}
```
### Поиск фильмов и сериалов по названию

Запрос
```sh
https://api.kinopoisk.dev/v1.4/movie/search?page=1&limit=1&query=Шерлок
```
Ответ
```sh
{
    "docs": [
        {
            "id": 502838,
            "name": "Шерлок",
            "alternativeName": "Sherlock",
            "enName": "Sherlock",
            "type": "tv-series",
            "year": 2010,
            "description": "События разворачиваются в наши дни. Он прошел Афганистан, остался инвалидом. По возвращении в родные края встречается с загадочным, но своеобразным гениальным человеком. Тот в поиске соседа по квартире. Лондон, 2010 год. Происходят необъяснимые убийства. Скотланд-Ярд без понятия, за что хвататься. Существует лишь один человек, который в силах разрешить проблемы и найти ответы на сложные вопросы.",
            "shortDescription": "Гений-социопат знакомит соседа с миром частного сыска. Бенедикт Камбербэтч в одном из лучших шоу XXI века",
            "movieLength": 0,
            "isSeries": true,
            "ticketsOnSale": false,
            "totalSeriesLength": null,
            "seriesLength": 90,
            "ratingMpaa": null,
            "ageRating": 12,
            "top10": null,
            "top250": 13,
            "typeNumber": 2,
            "status": "completed",
            "names": [
                {
                    "name": "Шерлок"
                },
                {
                    "name": "Sherlock"
                },
                {
                    "name": "神探夏洛克",
                    "language": "CN",
                    "type": null
                },
                {
                    "name": "新福尔摩斯",
                    "language": "CN",
                    "type": null
                },
                {
                    "name": "Σέρλοκ",
                    "language": "GR",
                    "type": null
                },
                {
                    "name": "新福尔摩斯",
                    "language": "HK",
                    "type": null
                },
                {
                    "name": "شرلوک هلمز",
                    "language": "IR",
                    "type": null
                },
                {
                    "name": "Šerlokas",
                    "language": "LT",
                    "type": null
                },
                {
                    "name": "新世紀福爾摩斯",
                    "language": "TW",
                    "type": null
                },
                {
                    "name": "Sherlok Xolms",
                    "language": "UZ",
                    "type": null
                },
                {
                    "name": "셜록",
                    "language": "KR",
                    "type": null
                }
            ],
            "externalId": {
                "imdb": "tt1475582",
                "tmdb": 19885,
                "kpHD": "4b94994d2cfce2eda1181debca01d1ba"
            },
            "logo": {
                "url": "https://avatars.mds.yandex.net/get-ott/1534341/2a00000176f14b4a1e2a3f40dd652de59d6e/orig"
            },
            "poster": {
                "url": "https://image.openmoviedb.com/kinopoisk-images/1629390/f28c1ea2-47b0-49d5-b11c-9608744f0233/orig",
                "previewUrl": "https://image.openmoviedb.com/kinopoisk-images/1629390/f28c1ea2-47b0-49d5-b11c-9608744f0233/x1000"
            },
            "backdrop": {
                "url": "https://image.openmoviedb.com/tmdb-images/original/hmLTIRtVyTHShJl2Wb8LHmvUgJm.jpg",
                "previewUrl": "https://image.openmoviedb.com/tmdb-images/w500/hmLTIRtVyTHShJl2Wb8LHmvUgJm.jpg"
            },
            "rating": {
                "kp": 8.857,
                "imdb": 9.1,
                "filmCritics": 0,
                "russianFilmCritics": 75,
                "await": null
            },
            "votes": {
                "kp": 629372,
                "imdb": 990421,
                "filmCritics": 0,
                "russianFilmCritics": 12,
                "await": 12
            },
            "genres": [
                {
                    "name": "детектив"
                },
                {
                    "name": "триллер"
                },
                {
                    "name": "драма"
                },
                {
                    "name": "криминал"
                }
            ],
            "countries": [
                {
                    "name": "Великобритания"
                },
                {
                    "name": "США"
                }
            ],
            "releaseYears": [
                {
                    "start": 2010,
                    "end": 2017
                }
            ]
        }
    ],
    "total": 284,
    "limit": 1,
    "page": 1,
    "pages": 284
}
```
### Получить названия стран по которым можно осуществлять поиск фильмов или сериалов
Запрос
```sh
https://api.kinopoisk.dev/v1/movie/possible-values-by-field?field=countries.name
```
Отпвет
```sh
[
    {
        "name": "Австралия",
        "slug": "Avstraliya"
    },
    {
        "name": "Австрия",
        "slug": "Avstriya"
    },
    {
        "name": "Азербайджан",
        "slug": "Azerbaydzhan"
    },
    ...
     {
        "name": "Югославия (ФР)",
        "slug": "Yugoslaviya-(FR)"
    },
    {
        "name": "Ямайка",
        "slug": "Yamayka"
    },
    {
        "name": "Япония",
        "slug": "Yaponiya"
    }
    ]
```
### Поиск сериала или фильма по id
Запрос
```sh
https://api.kinopoisk.dev/v1.4/movie/555
```
Ответ
```sh
{
    "status": null,
    "externalId": {
        "imdb": "tt0118715",
        "tmdb": 115,
        "kpHD": "41d4f2b47923dee781874861df2309a4"
    },
    "rating": {
        "kp": 7.803,
        "imdb": 8.1,
        "filmCritics": 7.4,
        "russianFilmCritics": 0,
        "await": null
    },
    "votes": {
        "kp": 304802,
        "imdb": 855720,
        "filmCritics": 192,
        "russianFilmCritics": 2,
        "await": 0
    },
    "backdrop": {
        "url": "https://image.openmoviedb.com/tmdb-images/original/nevS6wjzCxZESvmjJZqdyZ3RNQ6.jpg",
        "previewUrl": "https://image.openmoviedb.com/tmdb-images/w500/nevS6wjzCxZESvmjJZqdyZ3RNQ6.jpg"
    },
    "movieLength": 117,
    "images": {
        "framesCount": 51
    },
    "productionCompanies": [
        {
            "name": "Gramercy Pictures",
            "url": "https://www.themoviedb.org/t/p/original/nlG4JEjJ7SAFDehgIVvHFBPW23y.png",
            "previewUrl": "https://www.themoviedb.org/t/p/w500/nlG4JEjJ7SAFDehgIVvHFBPW23y.png"
        },
        {
            "name": "PolyGram Filmed Entertainment",
            "url": "https://www.themoviedb.org/t/p/original/sOg7LGESPH5vCTOIdbMhLuypoLL.png",
            "previewUrl": "https://www.themoviedb.org/t/p/w500/sOg7LGESPH5vCTOIdbMhLuypoLL.png"
        },
        {
            "name": "Working Title Films",
            "url": "https://www.themoviedb.org/t/p/original/16KWBMmfPX0aJzDExDrPxSLj0Pg.png",
            "previewUrl": "https://www.themoviedb.org/t/p/w500/16KWBMmfPX0aJzDExDrPxSLj0Pg.png"
        },
        {
            "name": "Universal Pictures",
            "url": "https://www.themoviedb.org/t/p/original/8lvHyhjr8oUKOOy2dKXoALWKdp0.png",
            "previewUrl": "https://www.themoviedb.org/t/p/w500/8lvHyhjr8oUKOOy2dKXoALWKdp0.png"
        }
    ],
    "spokenLanguages": [
        {
            "name": "English",
            "nameEn": "English"
        },
        {
            "name": "עִבְרִית",
            "nameEn": "Hebrew"
        },
        {
            "name": "Español",
            "nameEn": "Spanish"
        },
        {
            "name": "Deutsch",
            "nameEn": "German"
        }
    ],
    "id": 555,
    "type": "movie",
    "name": "Большой Лебовски",
    "description": "Лос-Анджелес, 1991 год, война в Персидском заливе. Главный герой по прозвищу Чувак считает себя совершенно счастливым человеком. Его жизнь составляют игра в боулинг и выпивка. Но внезапно его счастье нарушается, гангстеры по ошибке принимают его за миллионера-однофамильца, требуют деньги, о которых он ничего не подозревает, и, ко всему прочему, похищают жену миллионера, будучи уверенными, что «муж» выплатит за нее любую сумму.",
    "distributors": {
        "distributor": "Каравелла DDC",
        "distributorRelease": "Двадцатый Век Фокс СНГ, Юниверсал Пикчерс Рус"
    },
    "premiere": {
        "world": "1998-01-18T00:00:00.000Z",
        "russia": "2013-03-28T00:00:00.000Z",
        "bluray": "2011-11-10T00:00:00.000Z",
        "dvd": "2006-09-12T00:00:00.000Z"
    },
    "slogan": "It takes guys as simple as the Dude and Walter to make a story this complicated... and they'd really rather be bowling",
    "year": 1998,
    "budget": {
        "value": 15000000,
        "currency": "$"
    },
    "poster": {
        "url": "https://image.openmoviedb.com/kinopoisk-images/6201401/86be967f-598d-46f2-bc59-bc222e2ca837/orig",
        "previewUrl": "https://image.openmoviedb.com/kinopoisk-images/6201401/86be967f-598d-46f2-bc59-bc222e2ca837/x1000"
    },
    "facts": [
        {
            "value": "Рецепт коктейля «Белый русский» таков: 2 части водки, 1 часть кофейного ликера и 1 часть сливок.",
            "type": "FACT",
            "spoiler": false
        },
        ...
        {
            "value": "Когда Уолтер и Чувак стоят на скале с прахом Донни, в момент, когда Уолтер произносит речь, на их головах виден пепел от предыдущих дублей.",
            "type": "BLOOPER",
            "spoiler": true
        }
    ],
    "genres": [
        {
            "name": "комедия"
        },
        {
            "name": "криминал"
        }
    ],
    "countries": [
        {
            "name": "США"
        },
        {
            "name": "Великобритания"
        }
    ],
    "seasonsInfo": [],
    "persons": [
        {
            "id": 6652,
            "photo": "https://st.kp.yandex.net/images/actor_iphone/iphone360_6652.jpg",
            "name": "Джефф Бриджес",
            "enName": "Jeff Bridges",
            "description": "The Dude",
            "profession": "актеры",
            "enProfession": "actor"
        },
        ...
        {
            "id": 12020,
            "photo": "https://st.kp.yandex.net/images/actor_iphone/iphone360_12020.jpg",
            "name": "Джоэл Коэн",
            "enName": "Joel Coen",
            "description": null,
            "profession": "редакторы",
            "enProfession": "writer"
        }
    ],
    "lists": [
        "top500",
        "popular-films",
        "ozvucheno_kubik_v_kube"
    ],
    "typeNumber": 1,
    "alternativeName": "The Big Lebowski",
    "enName": null,
    "names": [
        {
            "name": "Большой Лебовски"
        },
        {
            "name": "The Big Lebowski"
        },
        {
            "name": "위대한 레보스키",
            "language": "KR",
            "type": null
        },
        {
            "name": "Den store Lebowski",
            "language": "NO",
            "type": null
        },
        {
            "name": "the big lebowski 1998",
            "language": "CN",
            "type": null
        },
        {
            "name": "El gran Lebowski",
            "language": "ES",
            "type": "Castilian Spanish"
        },
        {
            "name": "Böyük Lebowski",
            "language": "AZ",
            "type": null
        }
    ],
    "ageRating": 18,
    "ratingMpaa": "r",
    "sequelsAndPrequels": [],
    "updatedAt": "2024-04-14T00:01:54.489Z",
    "imagesInfo": {
        "framesCount": 51
    },
    "similarMovies": [
        {
            "id": 309423,
            "name": "После прочтения сжечь",
            "alternativeName": "Burn After Reading",
            "enName": null,
            "type": "movie",
            "poster": {
                "url": "https://avatars.mds.yandex.net/get-kinopoisk-image/1946459/f7cdf889-05fd-4f49-a54f-5c0851b072b5/orig",
                "previewUrl": "https://avatars.mds.yandex.net/get-kinopoisk-image/1946459/f7cdf889-05fd-4f49-a54f-5c0851b072b5/x1000"
            },
            "rating": {
                "kp": 6.862,
                "imdb": 7,
                "filmCritics": 6.9,
                "russianFilmCritics": 0,
                "await": null
            },
            "year": 2008
        },
        ...
        {
            "id": 86326,
            "name": "Счастливое число Слевина",
            "alternativeName": "Lucky Number Slevin",
            "enName": null,
            "type": "movie",
            "poster": {
                "url": "https://avatars.mds.yandex.net/get-kinopoisk-image/6201401/b902fcb3-ca6f-4247-8e70-69f84e67087e/orig",
                "previewUrl": "https://avatars.mds.yandex.net/get-kinopoisk-image/6201401/b902fcb3-ca6f-4247-8e70-69f84e67087e/x1000"
            },
            "rating": {
                "kp": 8.129,
                "imdb": 7.7,
                "filmCritics": 5.9,
                "russianFilmCritics": 80,
                "await": null
            },
            "year": 2005
        }
    ],
    "fees": {
        "world": {
            "value": 46142637,
            "currency": "$"
        },
        "usa": {
            "value": 17451873,
            "currency": "$"
        }
    },
    "shortDescription": "Тихого пофигиста по ошибке принимают за богача. Криминальная комедия братьев Коэн, которая разошлась на цитаты",
    "technology": {
        "hasImax": false,
        "has3D": false
    },
    "ticketsOnSale": false,
    "logo": {
        "url": "https://imagetmdb.com/t/p/original/9F1j9BF9BbKosEUaczFoBSYBd0o.png"
    },
    "watchability": {
        "items": [
            {
                "name": "Иви",
                "logo": {
                    "url": "https://avatars.mds.yandex.net/get-ott/2419418/0dfd1724-848f-4725-9160-abc571f41c11/orig"
                },
                "url": "https://www.ivi.ru/watch/90316?utm_source=yandex&utm_medium=wizard"
            }
        ]
    },
    "top10": null,
    "top250": null,
    "deletedAt": null,
    "audience": [
        {
            "count": 3731088,
            "country": "США"
        },
        {
            "count": 730064,
            "country": "Франция"
        },
        {
            "count": 684403,
            "country": "Испания"
        }
    ],
    "isSeries": false,
    "seriesLength": null,
    "totalSeriesLength": null,
    "networks": null,
    "videos": {
        "trailers": [
            {
                "url": "https://www.youtube.com/embed/GiwMMziJdqg",
                "name": "Park Circus Official Trailer",
                "site": "youtube",
                "type": "TRAILER"
            },
            {
                "url": "https://www.youtube.com/embed/3QY0ckqBgDw",
                "name": "Blu-ray Trailer",
                "site": "youtube",
                "type": "TRAILER"
            },
            {
                "url": "https://www.youtube.com/embed/VgSqm8-wXWA",
                "name": "DVD Trailer",
                "site": "youtube",
                "type": "TRAILER"
            },
            {
                "url": "https://www.youtube.com/embed/cd-go0oBF4Y",
                "name": "\"The Big Lebowski\" Official Trailer",
                "site": "youtube",
                "type": "TRAILER"
            },
            {
                "url": "https://www.youtube.com/embed/GiwMMziJdqg",
                "name": "Park Circus Official Trailer",
                "site": "youtube",
                "type": "TRAILER"
            },
            {
                "url": "https://www.youtube.com/embed/3QY0ckqBgDw",
                "name": "Blu-ray Trailer",
                "site": "youtube",
                "type": "TRAILER"
            },
            {
                "url": "https://www.youtube.com/embed/VgSqm8-wXWA",
                "name": "DVD Trailer",
                "site": "youtube",
                "type": "TRAILER"
            },
            {
                "url": "https://www.youtube.com/embed/cd-go0oBF4Y",
                "name": "\"The Big Lebowski\" Official Trailer",
                "site": "youtube",
                "type": "TRAILER"
            }
        ]
    }
}
```
### Поиск комментариев по id фильма
Запрос
```sh
https://api.kinopoisk.dev/v1.4/review?movieId=555&page=1&limit=1&selectFields=id&selectFields=title&selectFields=review&selectFields=date&selectFields=author
```
Отпвет
```sh
{
    "docs": [
        {
            "id": 3347330,
            "title": "Я не мистер Лебовски. Это вы мистер Лебовски. А я Чувак. Так меня и зовите.",
            "review": "Когда я слышу вопрос о любимом фильме, сразу вспоминаю Джеффа Бриджеса в домашнем халате, спокойно плавающего по дорожкам для боулинга в фильме братьев Коэн 'Большой Лебовски'.\r\n\r\nСобытия разворачиваются в Лос-Анджелесе в 1991 году, во время войны в Персидском заливе. Главный герой, также известный как Чувак, считает себя очень счастливым человеком, который проводит время, играя в боулинг и выпивая. Его счастье внезапно нарушается, когда гангстеры по ошибке принимают его за богатого человека с такой же фамилией - Лебовски, требуют деньги, о которых он ничего не знает, и похищают жену этого богатого человека, уверенные, что 'муж' заплатит любую сумму за её освобождение.\r\n\r\nЕсли вы знакомы с работами братьев Коэн или любите черный юмор, то 'Большой Лебовски' точно понравится. Главный герой ироничен и наслаждается жизнью, не претендуя на что-то особенное, и, кажется, подтрунивает над обществом, которое боится брать на себя ответственность. Не стоит рассматривать картину, как криминальный фильм или кино с интересными преступными сюжетами. Братья Коэн создали высокоинтеллектуальное кино с элементами криминала, комедии, драмы и, возможно, философии. Этот фильм можно смело назвать жемчужиной мирового кинематографа.\r\n\r\nНе каждому понравится этот фильм. Здесь я бы посоветовал задуматься. Если вы искушённый синефил и наслаждаетесь творчеством братьев Коэн, то вам стоит ознакомиться с их ранней работой. Но если вы выбираете фильм для легкого, расслабляющего вечера, то это не самый лучший выбор.",
            "date": "2024-02-01T09:22:36.000Z",
            "author": "bajduke2@gmail.com"
        }
    ],
    "total": 151,
    "limit": 1,
    "page": 1,
    "pages": 151
}
```
