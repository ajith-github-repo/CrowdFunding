

export const getPercentage = (num1,num2) =>{
  if(num2 === 0) return -1;
   return Math.ceil((num1 / num2) * 100);
}

export const formatQueryString = (queryInfo) => {

  let queryString = ''
  for (const query in queryInfo) {
      queryString = queryString + query +":" +queryInfo[query] +",";
  }

  queryString = queryString.substring(0,queryString.length-1);

  return queryString
}