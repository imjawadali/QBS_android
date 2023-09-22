import React from 'react'
export default function LoginScreen() {
  return (
    <>
     <div className='logincontainer'>
        <div className='header'>
            <div className='icon'>
                <image src="./Images/logo.png"></image>
            </div>
        </div>
        <div className='logindiv'>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
        {/* <link href='https://fonts.googleapis.com/css2?family=Poppins&display=swap' rel='stylesheet' /> */}
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;600;700&display=swap" rel="stylesheet"/>
            <div className='parentheading'>Sign in to Your Account</div>
            <form>
              <div className='iconemail'>
            <input  type="text" placeholder='Username' id='email' name='email' className='fa fa-envelope'>
            </input>
            </div>
            <input type="password" placeholder='Password' id='password' name='password'>
            </input>
            <button id='btn'>Sign in</button>
            <div className='text1'>Forget Password?</div>
            </form>
            <div className='footerdiv'>
                <div className='text2'>Dont't have an account?</div>
                <div className='text3'>Register here</div>
            </div>
        </div>
     </div>
    </>
  )
}
