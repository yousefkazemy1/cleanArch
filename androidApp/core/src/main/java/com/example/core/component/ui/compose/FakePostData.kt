package com.example.core.component.ui.compose

import com.example.core.model.MediaUI
import com.example.core.model.PostItemUI
import com.example.core.model.UserUI

fun getFakePostData(): List<PostItemUI> {
    return listOf(
        PostItemUI(
            id = 1,
            user = UserUI(
                id = 1,
                username = "yousefkazemy",
                profileImage = "https://media.licdn.com/dms/image/D4E35AQFvAzHLu4voXA/profile-framedphoto-shrink_200_200/0/1687761897336?e=1688936400&v=beta&t=tW9UpfyOqtLeK3HhLgxRmVreWuM-fyjCHws6nWyGwEE"
            ),
            title = "This is test title",
            mediaUI = MediaUI(
                image = "https://www.pakainfo.com/wp-content/uploads/2021/09/sample-image-url-for-testing-300x169.jpg",
                width = 300u,
                height = 169u
            )
        ),
        PostItemUI(
            id = 1,
            user = UserUI(
                id = 1,
                username = "mahsamobaraki",
                profileImage = "https://media.licdn.com/dms/image/C4E03AQGBCDDLHrKxaA/profile-displayphoto-shrink_200_200/0/1652368859046?e=1694044800&v=beta&t=ZuQiLjQAOdh3olfzjvRGIifvBI7GFfRXk3VjHZTLl68"
            ),
            title = "This is test title",
            mediaUI = MediaUI(
                image = "https://media.licdn.com/dms/image/D4E22AQHP-fq38fBIig/feedshare-shrink_800/0/1688296688703?e=1691020800&v=beta&t=E78Qgve1Iff_0NXkSNLhtbelHM0hzrzoYPfpLtGjVwM",
                width = 800u,
                height = 456u
            )
        ),
        PostItemUI(
            id = 1,
            user = UserUI(
                id = 1,
                username = "hasanaminiloo",
                profileImage = "https://media.licdn.com/dms/image/D4D03AQEROPDcpeUfHw/profile-displayphoto-shrink_100_100/0/1673253815579?e=1694044800&v=beta&t=wsasR_XIQQRUF0ckEC7FSYsxZiulWGkkLvtubXK-8sw"
            ),
            title = "This is test title",
            mediaUI = MediaUI(
                image = "https://media.licdn.com/dms/image/D4D22AQFeDRwEporfkA/feedshare-shrink_1280/0/1688400734529?e=1691020800&v=beta&t=64c9nMLtZFT3mn4OHxQhyO7xkr_znHoRS2BeHvfOTWY",
                width = 315u,
                height = 546u
            )
        ),
        PostItemUI(
            id = 1,
            user = UserUI(
                id = 1,
                username = "youssefnasser",
                profileImage = "https://media.licdn.com/dms/image/D4D03AQH1XOQAE7hM5Q/profile-displayphoto-shrink_100_100/0/1685475986694?e=1694044800&v=beta&t=pPYHuyxzSC-gREK81TbmMQKO7vPxI8vmvgjjOMANODg"
            ),
            title = "This is test title",
            mediaUI = MediaUI(
                image = "https://media.licdn.com/dms/image/D4D22AQHPVhYs1Bw3Ig/feedshare-shrink_800/0/1686104043977?e=1691020800&v=beta&t=gz_wJ8mekhUxabL1GFkQ3R-yPCLYPrSjsR1omrKb9Jw",
                width = 362u,
                height = 192u
            )
        ),
        PostItemUI(
            id = 1,
            user = UserUI(
                id = 1,
                username = "nedaaroozi",
                profileImage = "https://media.licdn.com/dms/image/D4D03AQGYlKXqWqiZ4g/profile-displayphoto-shrink_100_100/0/1684479944450?e=1694044800&v=beta&t=il0KGI8Ij5qdNqGTPeRFpIuY-7QwqRP4zh-WrfNLbdg"
            ),
            title = "This is test title",
            mediaUI = MediaUI(
                image = "https://media.licdn.com/dms/image/D4D22AQFDsPe_4HUhLg/feedshare-shrink_800/0/1688374671174?e=1691020800&v=beta&t=dKikqcZODkCsMHyJBzz6W2DPHxrB2EFonFgea1Gf7Vg",
                width = 800u,
                height = 800u
            )
        ),
    )
}