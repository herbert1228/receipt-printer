import React, { Fragment, useState, useEffect } from 'react'
import './App.css'
import { getReceipt } from './api/ConnectionUtil'
import { Item, Receipt, Modal, InputBox } from './components'

// TODO add item to list 
// TODO no duplicate item can be added

function App() {
  const [location, setLocation] = useState('CA')
  const [items, setItems] = useState([])
  const [modalShow, setModelShow] = useState(false)
  const [receipt, setReceipt] = useState({})
  const [newItem, setNewItem] = useState({name: '', price: '', qty: ''})

  useEffect(() => {
    setItems(prevItems => [...prevItems, {name: 'book', price: '17.99', qty: 1}])
    setItems(prevItems => [...prevItems, {name: 'potato chips', price: '3.99', qty: 1}])
  }, [])

  useEffect(() => {
    // setItems(prevItems => [...prevItems, {name: 'book', price: '17.99', qty: 1}])
    // setItems(prevItems => [...prevItems, {name: 'potato chips', price: '3.99', qty: 1}])
    window.scrollTo({behavior: 'smooth', top: document.body.scrollHeight})
  }, [items])

  const handleLocationChange = evt => {
    setLocation(evt.target.value)
  }

  const handleRemove = index => {
    const tmp = [...items]
    tmp.splice(index, 1)
    setItems(tmp)
  }

  const handleAddItem = () => {
    if (newItem.name && newItem.price && newItem.qty) {
      setItems([...items, {...newItem}])
      setNewItem({name: '', price: '', qty: ''})
    }
  }

  const generateReceipt = async () => {
    setReceipt(await getReceipt(location, items))
    setModelShow(true)
  }

  const hideModal = () => {
    setModelShow(false)
  }

  return (
    <Fragment>
      <div className='app'>

        <div className='location'>
          <div>Location</div>
          <div>
            <InputBox value={location} onChange={handleLocationChange} placeholder='location' />
          </div>
        </div>

        {/* Shopping cart items */}
        <div className='items-container'>

          <h4>Items</h4>

          <div className='item-list'>

            <div className='item-list-header'>
              <div></div>
              <div>Name</div>
              <div>Price</div>
              <div>Quantity</div>
              <div></div>
            </div>

            {items.map((item, index) => 
                <Item key={index} {...item} index={index} handleRemove={handleRemove} />  
            )}

            {/* Add item row */}
            <div className='item'>
              <div>-></div>
              <div><InputBox value={newItem.name} onChange={(evt) => setNewItem({...newItem, name: evt.target.value})} width='70%' placeholder='Name' /></div>
              <div><InputBox value={newItem.price} onChange={(evt) => setNewItem({...newItem, price: evt.target.value})} width='35%' placeholder='Price' /></div>
              <div><InputBox value={newItem.qty} onChange={(evt) => setNewItem({...newItem, qty: evt.target.value})} width='35%' placeholder='Quantity' /></div>
              <div>
                <div className='btn item-list-btn' onClick={handleAddItem}>
                  Add
                </div>
              </div>
            </div>

          </div>

        </div>

        {/* Button to get receipt via API */}
        <div className='btn btn-generate' onClick={generateReceipt}>
          Generate Receipt
        </div>
      </div>

      {/* Popup modal, hidden by default */}
      <Modal show={modalShow} handleClose={hideModal}>
        <Receipt {...receipt} />
      </Modal>
    </Fragment>
  );
}

export default App
