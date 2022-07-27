import Constants from '../constants/constants';
const constants = new Constants().getConfig();

const getDifferenceBetweenDays = (date1,date2) => {

    let isNegative = new Date(date2) - new Date(date1) < 0;


    const diffTime = Math.abs(new Date(date2) - new Date(date1));
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

    return isNegative ? (diffDays * -1) : diffDays;
}

const getDisplayDate = (date) => {
        const d = new Date(date);
        return d.toDateString();
    
}

const getDisplayTime = (date) => {
        const d = new Date(date);
        return d.toLocaleString('en-US', { hour: 'numeric', hour12: true });

}

const getDateInFormat = (date, format) => {

    const map = {
        mm: date.getMonth() + 1,
        dd: date.getDate(),
        yy: date.getFullYear().toString().slice(-2),
        yyyy: date.getFullYear()
    }
    return format.replace(/mm|dd|yyyy|yy/gi, matched => map[matched])
}

export {
    getDateInFormat,
    getDisplayDate,
    getDisplayTime,
    getDifferenceBetweenDays
}