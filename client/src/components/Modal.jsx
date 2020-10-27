import React from 'react'
import '../App.css'

const Modal = ({ handleClose, show, children }) => {

  return (
    <div className={show ? "modal" : "modal display-none"}>
      <section className="modal-main">
        {children}
        <div className='btn btn-exit-modal' onClick={handleClose}>Done</div>
      </section>
    </div>
  );
};

export default Modal;