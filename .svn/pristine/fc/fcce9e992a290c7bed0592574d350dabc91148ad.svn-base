/************************************************
		Script for Inserting New Grades
************************************************/

BEGIN TRAN
BEGIN TRY
	/************************************************
		 Inserting T1 Grade into the table
	************************************************/
	IF NOT EXISTS (SELECT 1 FROM Gal.Grade WHERE Grade = 'T1')
	BEGIN
		IF EXISTS (SELECT 1
					FROM Gal.Designation D
					WHERE ShortName = 'Sr PSE' )
		BEGIN
			INSERT INTO [Gal].[Grade]
					   ([Grade]
					   ,[DesignationId]
					   ,[IsActive])
			SELECT 'T1', D.Id, 1
			FROM Gal.Designation D
			WHERE ShortName = 'Sr PSE'
		END

		ELSE
		BEGIN
			PRINT 'Senior Principal Software Engineer designation does not exists, so we can not insert grade : T1'
		END
	END
	ELSE
	BEGIN
		PRINT 'T1 Grade already exists'
	END

	/************************************************
		Inserting T2 Grade into the table
	************************************************/
	IF NOT EXISTS (SELECT 1 FROM Gal.Grade WHERE Grade = 'T2')
	BEGIN
		IF EXISTS (SELECT 1
					FROM Gal.Designation D
					WHERE ShortName = 'Architect' )
		BEGIN		
			INSERT INTO [Gal].[Grade]
					   ([Grade]
					   ,[DesignationId]
					   ,[IsActive])
			SELECT 'T2', D.Id, 1
			FROM Gal.Designation D
			WHERE ShortName = 'Architect'
		END

		ELSE
		BEGIN
			PRINT 'Architect designation does not exists, so we can not insert grade : T2'
		END
	END
	ELSE
	BEGIN
		PRINT 'T2 Grade already exists'
	END

	/************************************************
		Inserting T3 Grade into the table
	************************************************/
	IF NOT EXISTS (SELECT 1 FROM Gal.Grade WHERE Grade = 'T3')
	BEGIN
		IF EXISTS (SELECT 1
					FROM Gal.Designation D
					WHERE ShortName = 'Architect' )
		BEGIN		
			INSERT INTO [Gal].[Grade]
					   ([Grade]
					   ,[DesignationId]
					   ,[IsActive])
			SELECT 'T3', D.Id, 1
			FROM Gal.Designation D
			WHERE ShortName = 'Architect'
		END

		ELSE
		BEGIN
			PRINT 'Architect designation does not exists, so we can not insert grade : T3'
		END
	END
	ELSE
	BEGIN
		PRINT 'T3 Grade already exists'
	END


	/************************************************
		Inserting T4 Grade into the table
	************************************************/
	IF NOT EXISTS (SELECT 1 FROM Gal.Grade WHERE Grade = 'T4')
	BEGIN
		IF EXISTS (SELECT 1
					FROM Gal.Designation D
					WHERE ShortName = 'Sr Architect' )
		BEGIN		
			INSERT INTO [Gal].[Grade]
					   ([Grade]
					   ,[DesignationId]
					   ,[IsActive])
			SELECT 'T4', D.Id, 1
			FROM Gal.Designation D
			WHERE ShortName = 'Sr Architect'
		END

		ELSE
		BEGIN
			PRINT 'Senior Architect designation does not exists, so we can not insert grade : T4'
		END
	END
	ELSE
	BEGIN
		PRINT 'T4 Grade already exists'
	END


	/************************************************
		Inserting T5 Grade into the table
	************************************************/
	IF NOT EXISTS (SELECT 1 FROM Gal.Grade WHERE Grade = 'T5')
	BEGIN
		IF EXISTS (SELECT 1
					FROM Gal.Designation D
					WHERE ShortName = 'Sr Architect' )
		BEGIN		
			INSERT INTO [Gal].[Grade]
					   ([Grade]
					   ,[DesignationId]
					   ,[IsActive])
			SELECT 'T5', D.Id, 1
			FROM Gal.Designation D
			WHERE ShortName = 'Sr Architect'
		END

		ELSE
		BEGIN
			PRINT 'Senior Architect designation does not exists, so we can not insert grade: T5'
		END
	END
	ELSE
	BEGIN
		PRINT 'T5 Grade already exists'
	END
COMMIT TRAN
END TRY
	
BEGIN CATCH
         SELECT ERROR_SEVERITY() 'ERROR_SEVERITY',ERROR_LINE() 'ERROR_LINE',ERROR_MESSAGE() 'ERROR_MESSAGE'
         ROLLBACK TRAN
END CATCH