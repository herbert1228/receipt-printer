import React from 'react';
import '../App.css';

const InputBox = ({ value, onChange, width = '100%', placeholder }) => {

    const innerStyle = {
        'width': width,
        'fontSize': '25px'
    }

    return (
        <input 
            className='input-custom' 
            style={innerStyle} 
            type="text" 
            value={value}
            placeholder={placeholder}
            onChange={onChange}
        />
    )
}

export default InputBox;