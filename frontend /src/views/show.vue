
<template>

    <body>
        <section>
            <img src="../assets/bg.jpg" id="bg">
            <img src="../assets/moon.png" id="moon">
            <img src="../assets/mountain.png" id="mountain">
            <img src="../assets/road.png" id="road">
            <h2 id="text">Travel thousands of miles, <br> and read thousands of books. <br><br>
             <span
                    style="color:white; font-size: 80px;">"Scroll Down To Library " <center><span
                            style="font-size:160px; ">︾</span> </center>
                </span></h2>

        </section>
        <div style="padding:150px">
            <h1 style="color:white; font-size: 40px">
                Tongji University Library Website
            </h1>
            <br>
            <div style="color:white; font-size:20px">

                Tongji University Library has rich collections, including various well-known large-scale comprehensive
                databases and various authoritative professional society publications full-text databases. Tongji
                University Library adheres to the tenet of "readers first, service first", and carries out literature
                circulation and reading, electronic reading, domestic and foreign interlibrary loan, reference
                consultation, scientific and technological novelty search, information teaching, etc., to provide
                all-round services for teachers and students in the school.
            </div>
        </div>

        <div>
            <center>
                <el-button @click="jump" round size="large">Enter The Library</el-button>
            </center>
        </div>



    </body>

</template>

<script>
import { method } from 'lodash';

export default {
    data() {
        return {
            bg: document.getElementById("bg"),
            moon: document.getElementById("moon"),
            mountain: document.getElementById("mountain"),
            road: document.getElementById("road"),
            text: document.getElementById("text"),

        };
    },

    mounted() {
        window.addEventListener('scroll', this.handleScroll, true)
    },
    methods: {
        handleScroll() {
            var value = window.scrollY;
            //bg.style.top = value * 0.5 + 'px';
            moon.style.left = -value * 0.5 + 'px';
            mountain.style.top = -value * 0.15 + 'px';
            road.style.top = value * 0.15 + 'px';
            text.style.top = value * 1 + 'px';
        },


        jump() {
            const userid = window.localStorage.getItem('userid')
            window.sessionStorage.setItem("userid",userid)
            if(!window.localStorage.getItem('userid'))
                this.$router.replace('/login')
            else
                this.$router.replace('/HomePage')
        }


    }
}
</script>


<style lang="less" scoped>
* {
    margin: 0;
    padding: 0;
    font-family: 'Poppins', sans-serif;
}

body {
    background: #0a2a43;
    min-height: 1500px;
}

section {
    position: relative;
    width: 100%;
    height: 100vh;
    overflow: hidden;
    display: flex;
    justify-content: center;
    align-items: center;
}

section:before {
    content: '';
    position: absolute;
    bottom: 0;
    width: 100%;
    height: 100px;
    background: linear-gradient(to top, #0a2a43, transparent);
    z-index: 10000;
}

section:after {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: #0a2a43;
    z-index: 10000;
    mix-blend-mode: color;
}

section img {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    object-fit: cover;
    pointer-events: none;
}

#text {
    position: relative;
    color: #fff;
    font-size: 2.5em;
    z-index: 1;
}

#road {
    z-index: 2;
}
</style>



