<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="resources/css/login.css" type="text/css" media="all">

<h1>Đăng Nhập & Đăng Ký</h1>
<div class="container w3layouts agileits">
    <div class="login w3layouts agileits">
        <h2>Đăng Nhập</h2>
        <form action="#" method="post">
            <input type="text" Name="Userame" placeholder="Tên Đăng Nhập" required="">
            <input type="password" Name="Password" placeholder="Mật Khẩu" required="">
        </form>
        <ul class="tick w3layouts agileits">
            <li>
                <input type="checkbox" id="brand1" value="">
                <label for="brand1"><span></span>Nhớ mật khẩu</label>
            </li>
        </ul>
        <div class="send-button w3layouts agileits">
            <form>
                <input type="submit" value="Đăng Nhập">
            </form>
        </div>
        <a href="#">Quên Mật Khẩu?</a>
        <div class="social-icons w3layouts agileits">
            <p>- Hoặc Đăng Nhập Với -</p>
            <ul style="display: flex;flex-wrap: wrap;justify-content: center;">
                <li><a href="#"><span class="icons w3layouts agileits"></span><span class="text w3layouts agileits">Facebook</span></a></li>
                <li class="twt w3ls"><a href="#"><span class="icons w3layouts"></span><span class="text w3layouts agileits">Twitter</span></a></li>
                <li class="ggp aits"><a href="#"><span class="icons agileits"></span><span class="text w3layouts agileits">Google+</span></a></li>
                <div class="clear"> </div>
            </ul>
        </div>
        <div class="clear"></div>
    </div>

    <div class="register w3layouts agileits">
        <h2>Đăng Ký</h2>
        <form action="#" method="post">
            <input type="text" Name="Name" placeholder="Tên Đăng Nhập" required="">
            <input type="text" Name="Email" placeholder="Email" required="">
            <input type="password" Name="Password" placeholder="Mật Khẩu" required="">
            <input type="password" Name="Password" placeholder="Nhập Lại Mật Khẩu" required="">
        </form>
        <div class="send-button w3layouts agileits">
            <form>
                <input type="submit" value="Đăng Ký">
            </form>
        </div>
        <p>Bằng cách nhấn đăng ký, bạn sẽ đồng ý với các <a class="underline w3layouts agileits" href="#">Điều Khoản</a> của chúng tôi.</p>
        <div class="clear"></div>
    </div>

    <div class="clear"></div>

</div>

<div class="footer w3layouts agileits">
    <p> &copy; 2019 Đăng Nhập Thành Viên. Thiết kế bởi <a href="http://tamviet.com.vn/" target="_blank">Tavi</a></p>
</div>
