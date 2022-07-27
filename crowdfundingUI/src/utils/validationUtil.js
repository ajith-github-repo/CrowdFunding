import {getDifferenceBetweenDays,getDateInFormat} from '../utils/dateUtil';

const descriptionValidator = (description) => {
    if (!description || description.length < 20 || description.length > 5000) {
      return 'Invalid description , length can range from 20 to 5000 characters';
    }

    return null;
};

const titleValidator = (title) => {
  if (!title || title.length < 5 || title.length > 50) {
    return 'Invalid title , length can range from 5 to 50 characters';
  }

  return null;
};

const taglineValidator = (tagline) => {
  if (!tagline || tagline.length < 10 || tagline.length > 100) {
    return 'Invalid Tagline , length can range from 10 to 100 characters';
  }

  return null;
};


const amountValidator = (amount) => {
 try{
    if(parseInt(amount) && parseInt(amount) > 0){
      return null;
    } 
 }catch(ex){
    return 'Invalid Amount';
 }

 return 'Invalid Amount'; 
}

const nameValidator = (name) => {
  if (!name || name.length === 0) {
    return 'Invalid name';
  }

  return null;
};

const expiryDateValidator = (expiryDate) =>{

    if(!expiryDate || getDifferenceBetweenDays(new Date(),new Date(expiryDate)) <= 0) {
        return 'Invalid expiry date'
    }
    return null;
}

const tagsValidator = (tags) => {
  if(tags && tags.split(",").length > 0){
      return null;
  }
  return 'Invalid Tags,Please Provide One'; 
}
  const emailValidator = (email) => {
    if (!email || email.length === 0) {
      return 'Email cannot be empty';
    }
  
    const isEmailValid = email
    .toLowerCase()
    .match(
      /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)

  
    return !isEmailValid ?  'Invalid Email Format' : null;
  };


  const passwordValidator = (password) => {
    if (!password || password.length === 0) {
      return 'Password cannot be empty';
    }
  
    const isPasswordValid = password
    .toLowerCase()
    .match(
      /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/)

  
    return !isPasswordValid ?  'Password Must have min 8 chars with atleast one character and number ' : null;
  };

  export {
    nameValidator,
    emailValidator,
    passwordValidator,
    descriptionValidator,
    taglineValidator,
    titleValidator,
    amountValidator,
    expiryDateValidator,
    tagsValidator
  };