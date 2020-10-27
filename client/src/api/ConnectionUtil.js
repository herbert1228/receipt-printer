// get REACT_APP_API_URL from .env file
const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';

export const getReceipt = async (location, items) => {
  const response = await fetch(API_URL + '/get_receipt', {
    method: 'POST',
    mode: 'cors',
    headers: {
      'Content-Type': 'application/json'
    },
    
    body: JSON.stringify({ location, items })
  })

  return response.json();
}