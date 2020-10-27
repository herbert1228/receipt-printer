import React from 'react'
import '../App.css'
import { Item } from '.'

const Receipt = ({ location, items = [], subTotal = 0, tax = 0, total = 0 }) => {

  return (
    <div className='receipt'>
      <div>Receipt</div>
      {items.map((item, index) => 
        <Item key={item.name} {...item} index={index} />
      )}
      <div>
        <div>Sub Total: {Number(subTotal).toFixed(2)}</div>
        <div>Tax: {Number(tax).toFixed(2)}</div>
        <div>Total: {Number(total).toFixed(2)}</div>
      </div>
    </div>
  );
};

export default Receipt;