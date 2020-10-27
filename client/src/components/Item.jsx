import React from 'react';
import '../App.css';

const Item = ({ index, name, price, qty, handleRemove }) => {

    // handleRemove = undefined in popup modal
    const overridenStyle =  {gridTemplateColumns: handleRemove ? null : '1fr 4fr 4fr 1fr'} 

    return (
      <div className='item' style={overridenStyle}>
        <div>{index + 1}</div>
        <div>{name}</div>
        <div>{price}</div>
        <div>{qty}</div>
        {handleRemove &&
          <div>
            <div className='btn item-list-btn' onClick={() => handleRemove(index)}>X</div>
          </div>
        }
      </div>
    )
}

export default Item;