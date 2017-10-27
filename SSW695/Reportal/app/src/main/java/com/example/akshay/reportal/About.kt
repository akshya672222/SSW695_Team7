package com.example.akshay.reportal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_aboutus.*
import kotlinx.android.synthetic.main.activity_settings.*
import android.view.Gravity



class About : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aboutus)
        val alertDialog = AlertDialog.Builder(this ).create()

        akshaybtn.setOnClickListener(){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("AKSHAY SUNDERWANI")
            builder.setMessage("Hello, everyone, I am from India, I did my under graduation in computer science in 2014, " +
                    "I have work experience of 2 years as an iOS developer (Objective-C and swift). Worked on both iPhone and " +
                    "universal ios applications. I love to travel. play games, read comics and listen to music. \n" +
                    "\n" +
                    "Programming languages I know:\n" +
                    "C++, Java, Objective-C, Swift, HTML, CSS, Python\n" +
                    "\n" +
                    "I have used Pyunit for Python and unit test and UI testing tools by Xcode for ios development.\n" +
                    "\n" +
                    "You can contact me on +1-201-742-2822\n" +
                    "Email me on: asunderw@stevens.edu")
            builder.setPositiveButton("OK", null)
            val dialog = builder.show()

            // Must call show() prior to fetching text view
            val messageView = dialog.findViewById<View>(android.R.id.message) as TextView?
            messageView!!.gravity = Gravity.CENTER
        }

        celestebtn.setOnClickListener(){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("CELESTE SAKHILE")
            builder.setMessage("Hi! I've predominantly lived my life in Dubai, one of the most amazing places to live. " +
                    "In Dubai, along with schooling, I've also gained an undergraduate degree in computer science. " +
                    "I'm currently a software engineering major and this is my third semester at Stevens. As for my software " +
                    "experiences, I've worked at Nomura an investment bank for the summer as an analyst. It was a great " +
                    "experience as I've gained valuable pragmatic knowledge through the program and made some amazing new friends. \n" +
                    "\n" +
                    "Apart from this, I'm an avid fan of puzzles and riddles. Hence, I'm a proud owner of all the " +
                    "official Rubik's cubes and the Rubik's 360. Although I own all the cubes I'm still in the process of " +
                    "learning how to solve them. Along with this, I'm also very passionate about art and love to spend my spare time" +
                    " doodling and drawing so if you're interested you could always check out my Instagram page. \n" +
                    "\n" +
                    "It's not just art, I love everything that's even remotely related to it. From color pencils, fountain pens," +
                    " inks, markers, and paint to sketchbooks, stationery, and all sorts of art supplies. And this passion for art " +
                    "just doesn't end with this, rather it expands into the world of movies, music, TV shows, mangas, cartoons, anime, " +
                    "and comic books.\n" +
                    "\n" +
                    "So, if you'd like to geek out about any of the things I've mentioned or want to just talk about the things that " +
                    "you're interested in I'm the person to talk to. PS: I do tend to talk a lot so, fear not there won't really be any " +
                    "awkward silences! :)")
            builder.setPositiveButton("OK", null)
            val dialog = builder.show()

            // Must call show() prior to fetching text view
            val messageView = dialog.findViewById<View>(android.R.id.message) as TextView?
            messageView!!.gravity = Gravity.CENTER
        }

        mahabtn.setOnClickListener(){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("MAHA ALIDRISI")
            builder.setMessage("Hi, My name is Maha. I hold a bachelors degree in Computer Information Systems. " +
                    "I worked as a systems analyst for a year and I'm currently doing masters in Software Engineering." +
                    " I've learned many programming languages including Java, C++, PHP, JavaScript and my favorite one is C#. " +
                    "In the past two years, I have worked on couple of web development projects and " +
                    "I have little to no experience about test driven development. \n" +
                    "I love traveling and learning about new cultures.")
            builder.setPositiveButton("OK", null)
            val dialog = builder.show()

            // Must call show() prior to fetching text view
            val messageView = dialog.findViewById<View>(android.R.id.message) as TextView?
            messageView!!.gravity = Gravity.CENTER
        }

        pranaybtn.setOnClickListener(){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("PRANAY BURE")
            builder.setMessage("Hi my name is Pranay but you can call me PRAN. Before joining Stevens I was working for " +
                    "an MNC Cognizant Technology Solutions based in Pune, India as a Programmer Analyst. Majority of my " +
                    "projects were related to Banking and Financial domain with Mainframe technology. Also, I have experience " +
                    "in handling C, C#, Java projects. \n" +
                    "\n" +
                    "Technical stuff I am interested in are Python, Big Data, R and Automation testing tools.\n" +
                    "\n" +
                    "In my leisure time I love doing Photography (mostly portrait), reading about tech tycoons and " +
                    "playing cricket (moreover related to Baseball). I also enjoy meeting new people and interacting with them.\n" +
                    "\n" +
                    "As a sports enthusiast, I would love to learn Skateboarding (PM if you know how to do skateboarding).")
            builder.setPositiveButton("OK", null)
            val dialog = builder.show()

            // Must call show() prior to fetching text view
            val messageView = dialog.findViewById<View>(android.R.id.message) as TextView?
            messageView!!.gravity = Gravity.CENTER
        }

        prateekbtn.setOnClickListener(){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("PRATEEK TYAGI")
            builder.setMessage("I am a graduate student at Stevens Institute of Technology pursuing Software Engineering. " +
                    "I completed my Bachelor's (B.Tech) in Computer Science and Engineering in 2013. \n" +
                    "\n" +
                    "During my undergrad, I did 2 internships. My first internship was at Jubilant Energy, where I was " +
                    "allotted the project of developing an “Automated Workflow Application” on .NET framework, which was my " +
                    "first experience of working on a live application. My second internship was at Bechtel, where I again " +
                    "worked on creating a live application called Software Compliance Tool on .NET framework. \n" +
                    "\n" +
                    "After the completion of the undergraduate degree, I joined Corporate Executive Board (CEB). My key " +
                    "responsibilities included developing and managing CEB’s member-facing digital assets that include over " +
                    "50 member-facing websites. I was trained in web development and design tools such as HTML/CSS, XML, JavaScript," +
                    " Photoshop, TeamSite Content Management System (CMS).\n" +
                    "\n" +
                    "I left CEB in December 2014 and joined BlackRock’s Digital Marketing department where I worked until August " +
                    "2016. My role revolved around the website development and administration of BlackRock’s public sites. I expanded" +
                    " my knowledge in SEO, Google Analytics, user design, and experience etc. and partnered on content consumption" +
                    " patterns to provide an optimal user experience. \n" +
                    "\n" +
                    "I also partnered with a chemical based trading company in establishing chemical based e-commerce start-up which " +
                    "aims at building the biggest chemical marketplace in India with maximum online reach. I majorly focused on leading " +
                    "and managing the website that was under construction and overlooked the team responsible for developing the site" +
                    " along with building content and digital marketing strategy for the same.\n" +
                    "\n" +
                    "I am a big football (read soccer) fan and support Arsenal FC and also follow all major football competitions around " +
                    "the world. I also love rock and metal music and hope to see some of my favorite bands perform live.")
            builder.setPositiveButton("OK", null)
            val dialog = builder.show()

            // Must call show() prior to fetching text view
            val messageView = dialog.findViewById<View>(android.R.id.message) as TextView?
            messageView!!.gravity = Gravity.CENTER
        }

        reportalbtn.setOnClickListener(){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("REPORTAL")
            builder.setMessage("Reportal is an application that aims to assist the Stevens community by providing an easy way for students " +
                    "to report issues on campus. The application will have two main interfaces, one for the students and the other is for " +
                    "the maintenance team.\n" +
                    "\n" +
                    "The student’s interface will allow students to report in campus issues such as, infrastructural issues, service issues, " +
                    "etc. through the application. They will also be able to post a picture of the damaged item, provide a description and " +
                    "location of the reported issue. The application will also allow students to receive alerts about the status of their " +
                    "reported issues. Application will be available on Android platform for Students.\n" +
                    "\n" +
                    "As for the maintenance team, there will be a web portal which will allow the team to check reported issues and assign " +
                    "them to maintenance personnel. A history of the resolved and remaining issues will be maintained through this portal.\n")
            builder.setPositiveButton("OK", null)
            val dialog = builder.show()

            // Must call show() prior to fetching text view
            val messageView = dialog.findViewById<View>(android.R.id.message) as TextView?
            messageView!!.gravity = Gravity.CENTER
        }

        sonalibtn.setOnClickListener(){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("SONALI PATIL")
            builder.setMessage("Hello, I am Sonali Patil Fall 2016 graduate student. My major is Software Engineering. " +
                    "I have 3 years of experience in Java development with Yodlee, Bangalore. Yodlee provides finApps for" +
                    " Personal Finance Managment. \n" +
                    "I love coding with Java. I am learning few new languages including Python and javascript. \n" +
                    "In my spare time, I like to listen to music, sing songs and watch movies.\n" +
                    "I was a national level player of Handball. I used to contribute some of my time for social" +
                    " activities conducted by college NSS(National Service scheme) group.")
            builder.setPositiveButton("OK", null)
            val dialog = builder.show()

            // Must call show() prior to fetching text view
            val messageView = dialog.findViewById<View>(android.R.id.message) as TextView?
            messageView!!.gravity = Gravity.CENTER
        }

        gregbtn.setOnClickListener(){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("GREGG VESONDER")
            builder.setMessage("Gregg Vesonder is an Industry Professor in Software Engineering and Cyber-Physical Systems at " +
                    "Stevens Institute of Technology.  He also serves as an Adjunct Professor at the University of Pennsylvania " +
                    "in Computer and Information Science.  Prior to this role he served as Executive Director of the Cloud Platforms " +
                    "Research Department at AT&T Labs Research.  His Department at AT&T Labs Research focused both on Cloud Platforms" +
                    " and Mobile and Pervasive Systems.\n" +
                    "\n" +
                    "Vesonder’s career at AT&T spanned 35 years and a variety of roles.  Over the years Vesonder led organizations " +
                    "in a variety of areas including parallel cluster computing, speech recognition and text to speech, sensor " +
                    "networks, C++ compiler development, Artificial Intelligence Software Development Environments, Logic Based " +
                    "Tools and Systems, and Expert Systems. Vesonder’s work on artificial intelligence led to Bell Labs first " +
                    "commercial expert system, ACE.  He received a Bell Labs Fellow in 1990 for “pioneering work in artificial " +
                    "intelligence applications and technology and outstanding contributions in enabling organizations throughout " +
                    "AT&T to realize benefits from this technology.”   He was later awarded an AT&T Fellow for this work. Vesonder " +
                    "also was involved with AT&T’s a2bmusic venture initially as Vice President, Research and Development and later " +
                    "as Chief Technology Officer.  a2bmusic involved digital music distribution using audio compression software (AAC) " +
                    "and Digital Rights Management software both developed internally.\n" +
                    "\n" +
                    "His professional and research activities emphasize software, software engineering and the relationship of small " +
                    "systems (embedded,  sensor based) to large  scale systems particularly in Cyber-Physical and Socio-Technical " +
                    "domains. Vesonder also is interested in applying these techniques, learning by doing and social media to graduate" +
                    " and post graduate education and STEM for grade school and beyond.\n" +
                    "\n" +
                    "Dr. Vesonder has authored over 40 research papers.  He is both a Bell Labs and an AT&T Fellow.  He has and is " +
                    "serving on multiple program committees, was a member of the editorial board of the International Journal of " +
                    "Information Quality, and the International Journal of Computer Systems Science and Engineering.  He was associate " +
                    "editor in charge of Telecommunications and Network Management of the journal, Intelligent Systems Review.  He also " +
                    "served as a guest editor of the IEEE Communications Magazine. ")
            builder.setPositiveButton("OK", null)
            val dialog = builder.show()

            // Must call show() prior to fetching text view
            val messageView = dialog.findViewById<View>(android.R.id.message) as TextView?
            messageView!!.gravity = Gravity.CENTER
        }

        rafifbtn.setOnClickListener(){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("RAFIF ARAB")
            builder.setMessage("My name is Rafif Arab, I have a bachelor degree in computer information systems. " +
                    "I'm a graduate student in software engineering. My teammates and I developed many projects and " +
                    "learned many programming languages, Python is my favorite. I have worked in an insurance company for " +
                    "a year and a half as a business developer. One thing that most people don't know about me is I'm a big " +
                    "fan of sports especially tennis.")
            builder.setPositiveButton("OK", null)
            val dialog = builder.show()

            // Must call show() prior to fetching text view
            val messageView = dialog.findViewById<View>(android.R.id.message) as TextView?
            messageView!!.gravity = Gravity.CENTER
        }

    }

}