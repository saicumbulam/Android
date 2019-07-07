module Milkrun
  class S3Package
    attr_reader :version_code, :file_path

    def initialize(version_code:, file_path:)
      @version_code, @file_path = version_code, file_path
    end

    # Uploads a build to S3.
    def upload
      Milkrun.say "Uploading package to #{Milkrun.bucket} bucket"

      File.open(file_path, 'rb') do |file|
        Milkrun.s3_client.put_object(
          body: file,
          bucket: Milkrun.bucket,
          content_type: "application/vnd.android.package-archive",
          key: package_key
        )
      end

      Milkrun.say "Package uploaded to s3://#{Milkrun.bucket}/#{package_key}"
    end

    protected

    def package_key
      "#{version_code}/Kickstarter-#{version_code}.apk"
    end
  end
end
