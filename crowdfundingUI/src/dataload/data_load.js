const axios = require('axios');
var fs = require('fs');
var users;


users = JSON.parse(fs.readFileSync('./users.json', 'utf8'));
projects = JSON.parse(fs.readFileSync('projects.json', 'utf8'));

async function load(user,project){

    try{
        let resp = await axios({
            url: 'http://localhost:9191/auth/signUp',
            method: 'post',
            timeout: 800000,
            headers: {
                'Content-Type': 'application/json',
            },
            data:user
        })
    
        delete user.firstName;
        delete user.lastName;
        let response = await axios({
            url: 'http://localhost:9191/auth/login',
            method: 'post',
            headers: {
                'Content-Type': 'application/json',
            },
            data:user
        })

        let token = response.headers['authorization']
        token = token.split("Bearer ")[1].trim();
        response = await axios.post('http://localhost:9191/api/projects/',data = project,{
            headers:{
                'Authorization': `Bearer ${token}`,
                'Content-Type':'application/json'
            },
            
        });

    }catch(e){
        console.log(e)
    }
    
     

};


users.map((data,i) => {
    if(i != 3) load(data,projects[i]).then(x => console.log(' is complete')).catch(x => console.log(x))
})