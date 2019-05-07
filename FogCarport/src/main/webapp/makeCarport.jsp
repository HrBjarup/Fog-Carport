<%-- 
    Document   : makeCarport
    Created on : 12-04-2019, 16:39:35
--%>

<jsp:include page='/header.jsp'></jsp:include>

    <div class="d-flex justify-content-center" >
        <!-- Form start -->
        <form action="FrontController" method="post" id="carport-form">

            <!-- Hidden input: &command=simpleorder -->
            <input type="hidden" name="command" value="simpleorder">

            <h1 class="h1">Bestil Carport</h1>

            <!-- Height 
            <div class="form-group">
                <label for="InputHeight">H�jde i cm</label>
                <input type="number"class="form-control" id="input-height" placeholder="Indtast h�jde" name="height">
                <p hidden class="text-error-color text-error-size">H�jde skal v�re mellem 200cm og 300cm</p>
            </div> -->

            <!-- Length 
            <input type="number" class="form-control" id="input-length" placeholder="Indtast l�ngde" name="length"> -->
            <div class="form-group">
                <label for="InputLength">L�ngde i cm</label>
                <select class="form-control" id="input-length" name="length"><option selected="selected" value="">V�lg l�ngde</option>              
                </select>
                <p hidden class="text-error-color text-error-size">L�ngde skal v�re mellem 240cm og 720cm</p>
            </div>

            <!-- Width 
            <input type="number" class="form-control" id="input-width" placeholder="Indtast bredde" name="width"> -->
            <div class="form-group">
                <label for="InputWidth">Bredde i cm</label>
                <select class="form-control" id="input-width" name="width"><option selected="selected" value="">V�lg bredde</option>
                </select>
                <p hidden class="text-error-color text-error-size">Bredde skal v�re mellem 240cm og 720cm</p>
            </div>

            <!-- Checkbox -->
            <div class="form-check">
                <input type="checkbox" class="form-check-input" id="check-skur" name="shed" value="y">
                <label class="form-check-label" for="CheckSkur">V�lg Skur</label>
            </div>

            
            <!-- Shed menu part -->
            <div hidden id="carport-shed-div">
                <h1 class="h1">Byg skur</h1>
                <div class="form-group">
                    <label for="ShedInputLength">L�ngde i cm</label>
                    <select class="form-control" id="shed-length" name="shed-length"><option selected="selected" value="">V�lg l�ngde</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="ShedInputWidth">Bredde i cm</label>
                    <select class="form-control" id="shed-width" name="shed-width"><option selected="selected" value="">V�lg bredde</option>
                    </select>
                </div>
            </div>
            
            
            <!-- Button to submit -->
            <button type="submit" class="btn btn-primary disabled" id="submit-btn" disabled style="margin-top: 5px;">Bestil Carport</button>
        </form>
        <!-- Form end -->
    </div>

<!-- The order of script imports is important -->
<script src="javascript/OrderValidation.js" type="text/javascript"></script>
<script src="javascript/OrderValidationShed.js" type="text/javascript"></script>
<jsp:include page='/footer.jsp'></jsp:include>
