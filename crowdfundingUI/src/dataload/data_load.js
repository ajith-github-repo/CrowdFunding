const axios = require('axios');
var fs = require('fs');



const users = JSON.parse(fs.readFileSync('./users.json', 'utf8'));
const projects = JSON.parse(fs.readFileSync('projects.json', 'utf8'));

const userIds = [];
const projectIds = [];
const tokens = {};
async function load(){

    try{

        await Promise.all(
            users.map(async (user,i) => {
                let resp = await axios({
                    url: 'http://localhost:9191/auth/signUp',
                    method: 'post',
                    timeout: 800000,
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    data:user
                })
                userIds.push(resp.data.userId);

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

                tokens[resp.data.userId] = token;

                response = await axios.post('http://localhost:9191/api/projects/',data = projects[i],{
                    headers:{
                        'Authorization': `Bearer ${token}`,
                        'Content-Type':'application/json'
                    },
                    
                });
        
                projectIds.push(response.data.projectId)
            })        
        )

        userIds.map(async userId => {
           for(let i=0;i<5;i++){
            let projectId = projectIds[Math.ceil(Math.random() * projectIds.length) % projectIds.length]  ;
                 

            response = await axios.post('http://localhost:9191/api/payments/',data = {
                amount:Math.ceil(Math.random() * 10000),
                payeeId:projectId,
                payerId:userId
            },{
                headers:{
                    'Authorization': `Bearer ${tokens[userId]}`,
                    'Content-Type':'application/json'
                },
                
            });
    

           }
        })        
    }catch(e){
        console.log(e)
    }
};



load().then(x => console.log('Load is complete')).catch(x => console.log(x)) 